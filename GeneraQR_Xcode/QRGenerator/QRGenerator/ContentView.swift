//
//  ContentView.swift
//  QRGenerator
//
//  Created by María Esther Guzmán  on 01/05/25.
//


import SwiftUI
import Firebase
import FirebaseAuth

struct ContentView: View {
    @State private var email = ""
    @State private var password = ""
    @State private var errorMessage = ""
    @State private var navigateToQR = false
    @State private var navigateToCrearCuenta = false
    @State private var navigateToRestablecerContrasena = false
    @State private var mostrarContrasena = false

    var body: some View {
        NavigationStack {
            ZStack {
                Color.white.ignoresSafeArea()

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
                    Text("Bienvenido Inicia Sesión")
                        .font(.largeTitle)
                        .bold()

                    HStack {
                        Image(systemName: "envelope")
                            .foregroundColor(.gray)
                        TextField("Correo electrónico", text: $email)
                            .autocapitalization(.none)
                            .foregroundColor(.black)
                    }
                    .padding()
                    .background(Color(.systemGray6))
                    .cornerRadius(10)
                    .padding(.horizontal)

                    HStack {
                        Image(systemName: "lock")
                            .foregroundColor(.gray)

                        if mostrarContrasena {
                            TextField("Contraseña", text: $password)
                                .foregroundColor(.black)
                        } else {
                            SecureField("Contraseña", text: $password)
                                .foregroundColor(.black)
                        }

                        Button(action: {
                            mostrarContrasena.toggle()
                        }) {
                            Image(systemName: mostrarContrasena ? "eye.slash" : "eye")
                                .foregroundColor(.gray)
                        }
                    }
                    .padding()
                    .background(Color(.systemGray6))
                    .cornerRadius(10)
                    .padding(.horizontal)

                    Button("Iniciar sesión") {
                        Auth.auth().signIn(withEmail: email, password: password) { _, error in
                            if let error = error {
                                errorMessage = mensajeDeErrorPersonalizado(error)
                            } else {
                                navigateToQR = true
                            }
                        }
                    }
                    .padding()
                    .frame(maxWidth: .infinity)
                    .background(Color.blue)
                    .foregroundColor(.white)
                    .cornerRadius(10)
                    .padding(.horizontal)

                    Button("¿Olvidaste tu contraseña?") {
                        navigateToRestablecerContrasena = true
                    }
                    .font(.footnote)
                    .foregroundColor(.gray)

                    Button("¿No tienes cuenta? Crea cuenta") {
                        navigateToCrearCuenta = true
                    }
                    .foregroundColor(.blue)
                    .padding(.top, 10)

                    if !errorMessage.isEmpty {
                        Text(errorMessage)
                            .foregroundColor(.red)
                            .multilineTextAlignment(.center)
                            .padding(.horizontal)
                    }

                    NavigationLink("", destination: GenerarQR(), isActive: $navigateToQR)
                        .opacity(0)
                    NavigationLink("", destination: CrearCuenta(), isActive: $navigateToCrearCuenta)
                        .opacity(0)
                    NavigationLink("", destination: RestablecerContrasena(), isActive: $navigateToRestablecerContrasena)
                        .opacity(0)
                }
                .padding()
            }
        }
    }

    // ✅ Función para traducir errores al español
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
    ContentView()
}


