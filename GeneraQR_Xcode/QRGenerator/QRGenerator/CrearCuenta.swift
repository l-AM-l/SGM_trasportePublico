//
//  CrearCuenta.swift
//  QRGenerator
//
//  Created by María Esther Guzmán  on 01/05/25.
//

import SwiftUI
import Firebase
import FirebaseAuth
import FirebaseDatabase

struct CrearCuenta: View {
    @State private var nombre = ""
    @State private var apellido = ""
    @State private var carrera = ""
    @State private var semestre = ""
    @State private var email = ""
    @State private var password = ""
    @State private var confirmPassword = ""
    @State private var errorMessage = ""
    @State private var cuentaCreada = false

    var body: some View {
        NavigationStack {
            ZStack {
                // Fondo blanco
                Color.white.ignoresSafeArea()

                // Círculos decorativos
                Circle()
                    .fill(Color.blue.opacity(0.3))
                    .frame(width: 300, height: 300)
                    .offset(x: -150, y: -350)

                Circle()
                    .fill(Color.green.opacity(0.3))
                    .frame(width: 200, height: 200)
                    .offset(x: 150, y: -250)

                Circle()
                    .fill(Color.blue.opacity(0.2))
                    .frame(width: 400, height: 400)
                    .offset(x: 100, y: 400)

                VStack(spacing: 20) {
                    Text("Crear Cuenta")
                        .font(.largeTitle)
                        .bold()
                        .padding(.top, 40)

                    Group {
                        TextField("Nombre", text: $nombre)
                        TextField("Apellido", text: $apellido)
                        TextField("Carrera", text: $carrera)
                        TextField("Semestre", text: $semestre)
                        TextField("Correo electrónico", text: $email)
                            .keyboardType(.emailAddress)
                            .autocapitalization(.none)
                        SecureField("Contraseña", text: $password)
                        SecureField("Confirmar contraseña", text: $confirmPassword)
                    }
                    .textFieldStyle(.roundedBorder)
                    .foregroundColor(.black)

                    Button("Registrar") {
                        if password != confirmPassword {
                            errorMessage = "Las contraseñas no coinciden."
                            return
                        }

                        Auth.auth().createUser(withEmail: email, password: password) { result, error in
                            if let error = error {
                                errorMessage = mensajeDeErrorPersonalizado(error)
                            } else if let user = result?.user {
                                // Guardar displayName
                                let nombreCompleto = "\(nombre) \(apellido)"
                                let changeRequest = user.createProfileChangeRequest()
                                changeRequest.displayName = nombreCompleto
                                changeRequest.commitChanges { error in
                                    if let error = error {
                                        errorMessage = "Error al guardar nombre: \(mensajeDeErrorPersonalizado(error))"
                                    } else {
                                        // Guardar en Realtime Database
                                        let ref = Database.database().reference()
                                        let uid = user.uid
                                        let datosUsuario: [String: Any] = [
                                            "nombre": nombre,
                                            "apellido": apellido,
                                            "carrera": carrera,
                                            "semestre": semestre,
                                            "email": email
                                        ]
                                        ref.child("usuarios").child(uid).setValue(datosUsuario) { error, _ in
                                            if let error = error {
                                                errorMessage = "Error al guardar datos: \(error.localizedDescription)"
                                            } else {
                                                cuentaCreada = true
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    .buttonStyle(.borderedProminent)

                    if !errorMessage.isEmpty {
                        Text(errorMessage)
                            .foregroundColor(.red)
                            .multilineTextAlignment(.center)
                            .padding(.top, 10)
                    }

                    NavigationLink("", destination: GenerarQR(), isActive: $cuentaCreada)
                        .opacity(0)
                }
                .padding()
            }
        }
    }

    // Función para traducir errores de Firebase
    func mensajeDeErrorPersonalizado(_ error: Error) -> String {
        let errorCode = AuthErrorCode(rawValue: (error as NSError).code)

        switch errorCode {
        case .wrongPassword:
            return "La contraseña es incorrecta. Intenta nuevamente."
        case .invalidEmail:
            return "El formato del correo electrónico no es válido."
        case .userNotFound:
            return "No existe una cuenta con este correo."
        case .emailAlreadyInUse:
            return "Este correo ya está registrado."
        case .weakPassword:
            return "La contraseña es demasiado débil. Usa al menos 6 caracteres."
        case .networkError:
            return "Problemas de conexión. Verifica tu red."
        default:
            return "Ocurrió un error. Intenta nuevamente."
        }
    }
}

#Preview {
    CrearCuenta()
}

