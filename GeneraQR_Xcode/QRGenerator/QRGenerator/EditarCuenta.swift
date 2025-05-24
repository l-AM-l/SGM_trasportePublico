//
//  EditarCuenta.swift
//  QRGenerator
//
//  Created by María Esther Guzmán  on 02/05/25.
//

import SwiftUI
import FirebaseAuth
import FirebaseDatabase

struct EditarCuenta: View {
    @State private var nombre = ""
    @State private var apellido = ""
    @State private var carrera = ""
    @State private var semestre = ""
    @State private var mensaje = ""

    var body: some View {
        ZStack {
            // Fondo degradado
            LinearGradient(
                gradient: Gradient(colors: [Color.blue.opacity(0.4), Color.green.opacity(0.4)]),
                startPoint: .topLeading,
                endPoint: .bottomTrailing
            )
            .ignoresSafeArea()

            VStack {
                // Tarjeta blanca
                VStack(spacing: 20) {
                    Text("Editar Cuenta")
                        .font(.title)
                        .bold()
                        .padding(.bottom, 30)

                    TextField("Nombre", text: $nombre)
                        .textFieldStyle(.roundedBorder)

                    TextField("Apellido", text: $apellido)
                        .textFieldStyle(.roundedBorder)

                    TextField("Carrera", text: $carrera)
                        .textFieldStyle(.roundedBorder)

                    TextField("Semestre", text: $semestre)
                        .textFieldStyle(.roundedBorder)

                    Button("Guardar cambios") {
                        guardarDatos()
                    }
                    .buttonStyle(.borderedProminent)

                    Text(mensaje)
                        .foregroundColor(mensaje.contains("✅") ? .green : .red)
                        .multilineTextAlignment(.center)
                }
                .padding()
                .background(Color.white)
                .cornerRadius(16)
                .shadow(radius: 10)
                .padding(.horizontal, 30)
            }
        }
        .onAppear {
            cargarDatos()
        }
    }

    func cargarDatos() {
        guard let uid = Auth.auth().currentUser?.uid else { return }

        let ref = Database.database().reference().child("usuarios").child(uid)
        ref.observeSingleEvent(of: .value) { snapshot in
            if let datos = snapshot.value as? [String: Any] {
                self.nombre = datos["nombre"] as? String ?? ""
                self.apellido = datos["apellido"] as? String ?? ""
                self.carrera = datos["carrera"] as? String ?? ""
                self.semestre = datos["semestre"] as? String ?? ""
            } else {
                self.mensaje = "No se pudieron cargar los datos."
            }
        }
    }

    func guardarDatos() {
        guard let uid = Auth.auth().currentUser?.uid else { return }

        let ref = Database.database().reference().child("usuarios").child(uid)
        let nuevosDatos: [String: Any] = [
            "nombre": nombre,
            "apellido": apellido,
            "carrera": carrera,
            "semestre": semestre
        ]

        ref.updateChildValues(nuevosDatos) { error, _ in
            if let error = error {
                mensaje = "Error al guardar: \(error.localizedDescription)"
            } else {
                mensaje = "Cambios guardados correctamente"
            }
        }

        let displayName = "\(nombre) \(apellido)"
        let changeRequest = Auth.auth().currentUser?.createProfileChangeRequest()
        changeRequest?.displayName = displayName
        changeRequest?.commitChanges(completion: nil)
    }
}

#Preview {
    EditarCuenta()
}

