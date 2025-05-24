# Sistema de Boletos QR para Transporte Público

## Descripción
Sistema compuesto por tres aplicaciones para la gestión de boletos electrónicos mediante códigos QR en transporte público.

## Componentes

### 1. Generador QR (iOS)
- Tecnología: Swift + Firebase
- Funciones:
  - Login/registro de usuarios
  - Generación de códigos QR
  - Gestión de perfil de usuario

### 2. Torniquete (Android)
- Tecnología: Kotlin + Firebase
- Funciones:
  - Validación de códigos QR
  - Control de acceso al transporte

### 3. QR Trip Monitor (Ionic)
- Tecnología: Ionic + Angular + Firebase
- Funciones:
  - Panel de administración
  - Monitoreo en tiempo real
  - Gestión de claves

### Permisos
{
  "rules": {
  	"usuarios": {
  		".read": "auth != null",
  		"$uid": {
    		".write": "$uid === auth.uid" //Solo el usuario dueño del registro puede modificarlo
  						}
						},
    "claves": {
    	".read": "auth != null", //Solo usuarios autenticados pueden leer y escribir 
      ".write": "auth != null"
    }
 		 }
}