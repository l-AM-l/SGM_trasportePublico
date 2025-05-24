//
//  GenerarQR.swift
//  QRGenerator
//
//  Created by María Esther Guzmán  on 01/05/25.

import SwiftUI
import FirebaseDatabase
import FirebaseAuth
import CoreImage.CIFilterBuiltins

struct GenerarQR: View {
    @State private var clave: String = ""
    @State private var qrImage: Image?
    @State private var error: String = ""
    @State private var mostrarEdicion = false
    @State private var qrValue: String = ""
    @Environment(\.dismiss) var dismiss

    let ref = Database.database().reference()
    let context = CIContext()
    let filter = CIFilter.qrCodeGenerator()

    var body: some View {
        let nombreUsuario = Auth.auth().currentUser?.displayName ?? "Usuario"

        NavigationStack {
            ZStack {
                LinearGradient(
                    gradient: Gradient(colors: [Color.blue.opacity(0.4), Color.green.opacity(0.4)]),
                    startPoint: .topLeading,
                    endPoint: .bottomTrailing
                )
                .ignoresSafeArea()
                VStack {
                    // Tarjeta blanca centrada
                    VStack(spacing: 20) {
                        Text("Hola, \(nombreUsuario)!")
                            .font(.headline)
                            .padding(.top)

                        Text("Generar Código QR")
                            .font(.title)
                            .bold()

                        if let qrImage = qrImage {
                            qrImage
                                .resizable()
                                .interpolation(.none)
                                .scaledToFit()
                                .frame(width: 200, height: 200)
                        } else {
                            Text("Presiona el botón para generar un QR.")
                                .foregroundColor(.gray)
                        }

                        if !qrValue.isEmpty { //
                            Text("QR usado: \(qrValue)") 
                                .font(.caption)
                        }

                        if !error.isEmpty {
                            Text(error)
                                .foregroundColor(.red)
                        }

                        Button("Generar otra clave") {
                            fetchClaveYGenerarQR()
                        }
                        .buttonStyle(.borderedProminent)

                        Divider().padding(.vertical)

                        Button("Cerrar sesión") {
                            do {
                                try Auth.auth().signOut()
                                dismiss()
                            } catch {
                                self.error = "Error al cerrar sesión: \(error.localizedDescription)"
                            }
                        }
                        .buttonStyle(.bordered)

                        NavigationLink("Editar cuenta", destination: EditarCuenta())
                            .padding(.bottom)
                    }
                    .padding()
                    .background(Color.white)
                    .cornerRadius(20)
                    .shadow(radius: 10)
                    .padding(.horizontal, 30)
                }
                .padding()
            }
        }
    }

    func fetchClaveYGenerarQR() {
        error = ""
        clave = ""
        qrValue = ""
        qrImage = nil

        ref.child("claves")
            .queryOrdered(byChild: "status")
            .queryEqual(toValue: "QR sin generar")
            .queryLimited(toFirst: 1)
            .observeSingleEvent(of: .value) { snapshot in
                guard let value = snapshot.value as? [String: Any],
                      let firstKey = value.keys.first,
                      let claveData = value[firstKey] as? [String: Any] else {
                    error = "⚠️ No hay claves disponibles con estatus 'QR sin generar'."
                    return
                }

                let claveObtenida = claveData["valor"] as? String ?? ""
                let qrObtenido = claveData["qr"] as? String ?? "" // Obtenemos el valor qr
                
                clave = claveObtenida
                qrValue = qrObtenido // Asignamos el valor qr
                qrImage = generarImagenQR(from: claveObtenida)

                
                ref.child("claves").child(firstKey).updateChildValues([
                    "status": "QR generado"
                ]) { error, _ in
                    if let error = error {
                        self.error = "Error al actualizar estatus: \(error.localizedDescription)"
                    }
                }
            }
    }

    func generarImagenQR(from string: String) -> Image? {
        filter.message = Data(string.utf8)
        if let outputImage = filter.outputImage,
           let cgimg = context.createCGImage(outputImage, from: outputImage.extent) {
            let uiImage = UIImage(cgImage: cgimg)
            return Image(uiImage: uiImage)
        }
        return nil
    }
}

#Preview {
    GenerarQR()
}
