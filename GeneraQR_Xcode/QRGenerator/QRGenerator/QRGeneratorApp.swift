//
//  QRGeneratorApp.swift
//  QRGenerator
//
//  Created by María Esther Guzmán  on 30/04/25.
//

import SwiftUI
import FirebaseCore
import FirebaseAnalytics
import FirebaseAuth

@main
struct QRGeneratorApp: App {

    init(){
        FirebaseApp.configure()
    }
    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}
