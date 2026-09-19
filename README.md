# ALX Creative Studio 4.0 Professional

Expansión profesional basada en el proyecto 3.0: herramientas inspiradas en flujos de edición raster/vectorial, tipografía ampliada, controles de transformación, fotomontaje avanzado, keyframes de animación y biblioteca de cosplay/patronaje textil.

# ALX Creative Studio para Android

Proyecto fuente de **ALX Creative Studio 3.0 Professional**.

## Características

- Aplicación Android independiente de ChatGPT.
- Contenido principal disponible sin conexión.
- Herramientas de dibujo, fotografía y cosplay/papercraft.
- Importación de imágenes y documentos PDF.
- Exportación de imágenes e impresión en varios tamaños de hoja.
- Figuras geométricas, degradados, paletas y modos de fusión.
- Selección, recorte, clonación y transformaciones fotográficas.
- Intros con fondos, audio, fuentes, escenarios y efectos de texto.
- Efectos PolyFX, explosión, movimiento aleatorio, aplastar/estirar, rebote y ondas.
- Estudio de diseño publicitario con formatos para afiches, historias, publicaciones, banners y tarjetas.
- Diseñador de wallpapers para celulares, computadoras, tabletas y pantallas ultrapanorámicas.
- Plantillas, zonas seguras, imágenes propias, degradados, partículas y exportación PNG.
- Importación de proyectos PDO para planificación y cálculo de materiales.
- Compatible con Android 6.0 o posterior.

## Compilación

1. Abrir la carpeta del proyecto con Android Studio.
2. Esperar a que Gradle termine de sincronizar.
3. Seleccionar **Build > Build APK(s)**.

También incluye el flujo `.github/workflows/build-apk.yml` para compilar el APK automáticamente mediante GitHub Actions.

## Identificación

- Nombre: ALX Creative Studio
- Paquete: `com.alx.creativestudio`
- Versión: 3.0 Professional
- Código de versión: 3

## ALX Creative Studio 4.1 — continuación Fase 1
Integrado sobre la primera entrega 4.1:
- Renombrar y bloquear/desbloquear capas.
- Modos de fusión configurables por capa.
- Máscaras de capa base: crear, invertir, activar/desactivar y quitar.
- Estructura inicial para grupos y capas de ajuste dentro del formato ALX.
- Panel de historial ampliado y persistencia mediante autoguardado.

Nota técnica: grupos, capas de ajuste y máscaras quedan representados de forma editable en el documento ALX. El render avanzado/pintado directo sobre máscara se seguirá reforzando con la evolución del motor, tal como prevé la hoja de ruta.


## ALX Creative Studio 4.1 — Fase 1 completada
- Documento ALX editable con metadatos de tamaño, DPI, orientación y sangrado.
- Autoguardado, recuperación, versiones manuales y deshacer/rehacer de documento.
- Cuadrícula, guías visuales y ajuste magnético almacenados en proyecto.
- Capas raster, grupos y capas de ajuste estructuradas; reordenar, renombrar, bloquear, ocultar, duplicar, vincular y opacidad/fusión.
- Máscaras de capa: crear, invertir, activar/desactivar, eliminar y modo de edición preparado.
- Combinar hacia abajo y crear copia aplanada sin destruir los originales.
- El formato .alx conserva la estructura editable en JSON.

Nota técnica: grupos, ajustes y máscaras quedan representados de forma no destructiva en el formato ALX. El motor WebView/Canvas actual impone límites para render avanzado comparable a editores nativos; esa evolución está contemplada en la fase de arquitectura/rendimiento.
