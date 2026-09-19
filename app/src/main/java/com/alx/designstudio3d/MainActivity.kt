package com.alx.designstudio3d

import android.app.*
import android.os.Bundle
import android.graphics.*
import android.graphics.drawable.GradientDrawable
import android.view.*
import android.widget.*
import android.content.Intent
import java.io.FileOutputStream
import java.io.File
import kotlin.math.*

class MainActivity : Activity() {
    private val bg = Color.rgb(9,16,29); private val panel = Color.rgb(17,29,48); private val cyan = Color.rgb(31,210,255)
    override fun onCreate(savedInstanceState: Bundle?) { super.onCreate(savedInstanceState); showHome(); intent.getStringExtra("sharedAssetPath")?.let { path -> Toast.makeText(this,"Recurso Creative recibido: ${File(path).name}",Toast.LENGTH_LONG).show() } }
    private fun tv(t:String, s:Float=16f, bold:Boolean=false)=TextView(this).apply{ text=t; textSize=s; setTextColor(Color.WHITE); setPadding(18,18,18,18); if(bold)setTypeface(null,Typeface.BOLD) }
    private fun button(t:String, action:()->Unit)=Button(this).apply{ text=t; setTextColor(Color.WHITE); background=GradientDrawable().apply{cornerRadius=20f;setColor(panel);setStroke(2,cyan)}; setOnClickListener{action()} }
    private fun showHome(){
        val root=LinearLayout(this).apply{orientation=LinearLayout.VERTICAL;setPadding(28,36,28,28);setBackgroundColor(bg)}
        root.addView(tv("ALX CREATIVE DESIGN STUDIO • 3D",28f,true)); root.addView(tv("Suite fusionada • recursos 2D/3D compartidos",15f));
        val shared=File(filesDir,"alx_shared"); val count=shared.listFiles()?.count{it.extension.lowercase() in listOf("png","jpg","jpeg","webp")}?:0
        root.addView(tv("Biblioteca compartida: $count recurso(s) Creative disponibles para texturas, referencias y decals.",13f))
        val modules=listOf("Centro de proyectos","Espacio de trabajo 3D  • NUEVO","Modelado 3D  • NUEVO","Materiales y texturas  • NUEVO","Arquitectura 2D/3D","Interiores","Exteriores y paisajismo","Diseño de muebles","Personajes y escultura","Iluminación y render","Animación","Cosplay y fabricación","Recorridos / AR-VR","Asistente ALX de Diseño")
        modules.forEachIndexed{i,n-> root.addView(button(n){ if(i in 1..3) showWorkspace(i) else if(i in 4..7) showDesignModule(i) else if(i==8) showCharacterModule() else if(i==9) showLightingModule() else if(i==10) showAnimationModule() else if(i==11) showCosplayModule() else if(i==12) showPresentationModule() else if(i==13) showAssistantModule() else Toast.makeText(this,"Módulo preparado para una etapa posterior",Toast.LENGTH_SHORT).show() },LinearLayout.LayoutParams(-1,70).apply{setMargins(0,7,0,7)}) }
        setContentView(ScrollView(this).apply{addView(root)})
    }
    private fun showWorkspace(tab:Int){
        val root=LinearLayout(this).apply{orientation=LinearLayout.VERTICAL;setBackgroundColor(bg)}
        val bar=LinearLayout(this).apply{orientation=LinearLayout.HORIZONTAL;setPadding(10,10,10,10);setBackgroundColor(panel)}
        bar.addView(button("‹ Inicio"){showHome()},LinearLayout.LayoutParams(0,60,1f)); bar.addView(tv(when(tab){2->"MODELADO 3D";3->"MATERIALES";else->"WORKSPACE 3D"},18f,true),LinearLayout.LayoutParams(0,60,2f)); root.addView(bar)
        val tools=HorizontalScrollView(this); val toolRow=LinearLayout(this).apply{orientation=LinearLayout.HORIZONTAL;setPadding(8,8,8,8)}; tools.addView(toolRow)
        val canvas=StudioView(this)
        fun tool(name:String, f:()->Unit){toolRow.addView(button(name){f()},LinearLayout.LayoutParams(145,58).apply{setMargins(4,0,4,0)})}
        if(tab==1){ tool("+ Cubo"){canvas.addShape(0)};tool("+ Esfera"){canvas.addShape(1)};tool("Mover"){canvas.mode=0};tool("Rotar"){canvas.mode=1};tool("Escalar"){canvas.mode=2};tool("Duplicar"){canvas.duplicate()};tool("Ocultar"){canvas.toggleHidden()};tool("Bloquear"){canvas.toggleLocked()};tool("Deshacer"){canvas.undo()};tool("Rehacer"){canvas.redo()};tool("Guardar"){canvas.saveNow()} }
        if(tab==2){ tool("Cubo"){canvas.addShape(0)};tool("Esfera"){canvas.addShape(1)};tool("Cilindro"){canvas.addShape(2)};tool("Extruir"){canvas.extrude()};tool("Inset"){canvas.inset()};tool("Bisel"){canvas.bevel()};tool("Subdividir"){canvas.subdivide()};tool("Simetría"){canvas.mirror()};tool("Suavizar"){canvas.smooth=true;canvas.invalidate()};tool("Unir"){canvas.booleanLabel="UNIÓN";canvas.invalidate()};tool("Restar"){canvas.booleanLabel="RESTA";canvas.invalidate()} }
        if(tab==3){ tool("Mate"){canvas.material="MATE";canvas.invalidate()};tool("Metálico"){canvas.material="METAL";canvas.invalidate()};tool("Vidrio"){canvas.material="VIDRIO";canvas.invalidate()};tool("Rugoso"){canvas.material="RUGOSO";canvas.invalidate()};tool("UV"){canvas.uv=!canvas.uv;canvas.invalidate()};tool("Normal"){canvas.normal=!canvas.normal;canvas.invalidate()} }
        root.addView(tools,LinearLayout.LayoutParams(-1,76)); root.addView(canvas,LinearLayout.LayoutParams(-1,0,1f));
        root.addView(tv("Gestos: toca para seleccionar • arrastra para transformar • pellizca para zoom • guardado automático activo",13f),LinearLayout.LayoutParams(-1,58)); setContentView(root)
    }

    private fun showDesignModule(tab:Int){
        val title=when(tab){4->"ARQUITECTURA 2D → 3D";5->"DISEÑO DE INTERIORES";6->"EXTERIORES Y PAISAJISMO";else->"DISEÑO DE MUEBLES"}
        val root=LinearLayout(this).apply{orientation=LinearLayout.VERTICAL;setBackgroundColor(bg)}
        val bar=LinearLayout(this).apply{orientation=LinearLayout.HORIZONTAL;setPadding(10,10,10,10);setBackgroundColor(panel)}
        bar.addView(button("‹ Inicio"){showHome()},LinearLayout.LayoutParams(0,60,1f));bar.addView(tv(title,18f,true),LinearLayout.LayoutParams(0,60,2f));root.addView(bar)
        val tools=HorizontalScrollView(this);val row=LinearLayout(this).apply{orientation=LinearLayout.HORIZONTAL;setPadding(8,8,8,8)};tools.addView(row)
        val canvas=DesignView(this,tab)
        fun tool(n:String,k:String){row.addView(button(n){canvas.addItem(k)},LinearLayout.LayoutParams(155,58).apply{setMargins(4,0,4,0)})}
        when(tab){
            4->{tool("+ Muro","Muro");tool("+ Columna","Columna");tool("+ Viga","Viga");tool("+ Puerta","Puerta");tool("+ Ventana","Ventana");tool("+ Escalera","Escalera");tool("+ Techo","Techo");tool("+ Nivel","Nivel");tool("Cotas","Cota");tool("Levantar 3D","3D")}
            5->{tool("Habitación","Habitación");tool("Piso","Piso");tool("Cielo","Cielo");tool("Cocina","Cocina");tool("Baño","Baño");tool("Dormitorio","Dormitorio");tool("Sala","Sala");tool("Oficina","Oficina");tool("Mueble","Mueble");tool("Luz","Iluminación")}
            6->{tool("Fachada","Fachada");tool("Terreno","Terreno");tool("Jardín","Jardín");tool("Árbol","Árbol");tool("Planta","Planta");tool("Camino","Camino");tool("Terraza","Terraza");tool("Piscina","Piscina");tool("Cerca","Cerca");tool("Luz exterior","Luz")}
            7->{tool("Módulo","Módulo");tool("Puerta","Puerta");tool("Cajón","Cajón");tool("Estante","Estante");tool("Material","Material");tool("Medidas","Medidas");tool("Despiece","Despiece")}
        }
        root.addView(tools,LinearLayout.LayoutParams(-1,76));root.addView(canvas,LinearLayout.LayoutParams(-1,0,1f));
        root.addView(tv("Editor conceptual con cuadrícula métrica • toca una herramienta para añadir elementos • base preparada para edición paramétrica y conversión 2D→3D",13f),LinearLayout.LayoutParams(-1,70));setContentView(root)
    }

    private fun showCharacterModule(){
        val root=LinearLayout(this).apply{orientation=LinearLayout.VERTICAL;setBackgroundColor(bg)}
        val bar=LinearLayout(this).apply{orientation=LinearLayout.HORIZONTAL;setPadding(10,10,10,10);setBackgroundColor(panel)}
        bar.addView(button("‹ Inicio"){showHome()},LinearLayout.LayoutParams(0,60,1f));bar.addView(tv("ESCULTURA Y PERSONAJES 3D",18f,true),LinearLayout.LayoutParams(0,60,2f));root.addView(bar)
        val tools=HorizontalScrollView(this);val row=LinearLayout(this).apply{orientation=LinearLayout.HORIZONTAL;setPadding(8,8,8,8)};tools.addView(row)
        val canvas=CharacterView(this)
        fun tool(n:String,k:String){row.addView(button(n){canvas.applyTool(k)},LinearLayout.LayoutParams(155,58).apply{setMargins(4,0,4,0)})}
        tool("Base humana","Base");tool("Cabeza","Cabeza");tool("Cuerpo","Cuerpo");tool("Cabello","Cabello");tool("Ropa","Ropa");tool("Accesorio","Accesorio");tool("Simetría","Simetría");tool("Inflar","Inflar");tool("Suavizar","Suavizar");tool("Pose","Pose");tool("Esqueleto","Rig")
        root.addView(tools,LinearLayout.LayoutParams(-1,76));root.addView(canvas,LinearLayout.LayoutParams(-1,0,1f));
        root.addView(tv("Etapa 8 integrada como editor inicial de personaje: base anatómica, escultura conceptual, simetría, ropa/accesorios, pose y estructura de rig.",13f),LinearLayout.LayoutParams(-1,72));setContentView(root)
    }
    private fun showCosplayModule(){
        val root=LinearLayout(this).apply{orientation=LinearLayout.VERTICAL;setBackgroundColor(bg)}
        val bar=LinearLayout(this).apply{orientation=LinearLayout.HORIZONTAL;setPadding(10,10,10,10);setBackgroundColor(panel)}
        bar.addView(button("‹ Inicio"){showHome()},LinearLayout.LayoutParams(0,60,1f));bar.addView(tv("COSPLAY Y FABRICACIÓN",18f,true),LinearLayout.LayoutParams(0,60,2f));root.addView(bar)
        val tools=HorizontalScrollView(this);val row=LinearLayout(this).apply{orientation=LinearLayout.HORIZONTAL;setPadding(8,8,8,8)};tools.addView(row)
        val canvas=CosplayView(this)
        fun tool(n:String,k:String){row.addView(button(n){canvas.addPart(k)},LinearLayout.LayoutParams(155,58).apply{setMargins(4,0,4,0)})}
        tool("Máscara","Máscara");tool("Casco","Casco");tool("Armadura","Armadura");tool("Hombrera","Hombrera");tool("Brazal","Brazal");tool("Escudo","Escudo");tool("Accesorio","Accesorio");tool("Escala cuerpo","Escala");tool("Separar piezas","Piezas");tool("Foam","Foam");tool("Papercraft","Papercraft");tool("Impresión 3D","3D Print");tool("Materiales","Materiales")
        root.addView(tools,LinearLayout.LayoutParams(-1,76));root.addView(canvas,LinearLayout.LayoutParams(-1,0,1f));
        root.addView(tv("Etapa 9 integrada como base de fabricación: piezas de cosplay, escalado corporal, separación, modos foam/papercraft/impresión 3D y estimación conceptual de materiales.",13f),LinearLayout.LayoutParams(-1,76));setContentView(root)
    }

    private fun showLightingModule(){
        val root=LinearLayout(this).apply{orientation=LinearLayout.VERTICAL;setBackgroundColor(bg)}
        val bar=LinearLayout(this).apply{orientation=LinearLayout.HORIZONTAL;setPadding(10,10,10,10);setBackgroundColor(panel)}
        bar.addView(button("‹ Inicio"){showHome()},LinearLayout.LayoutParams(0,60,1f));bar.addView(tv("ILUMINACIÓN Y RENDER",18f,true),LinearLayout.LayoutParams(0,60,2f));root.addView(bar)
        val tools=HorizontalScrollView(this);val row=LinearLayout(this).apply{orientation=LinearLayout.HORIZONTAL;setPadding(8,8,8,8)};tools.addView(row);val canvas=LightingView(this)
        fun tool(n:String,k:String){row.addView(button(n){if(k=="ExportCreative") exportRenderToCreative(canvas) else canvas.applyTool(k)},LinearLayout.LayoutParams(155,58).apply{setMargins(4,0,4,0)})}
        tool("Sol","Sol");tool("Puntual","Puntual");tool("Área","Área");tool("Spot","Spot");tool("Sombras","Sombras");tool("Ambiente","Ambiente");tool("HDRI","HDRI");tool("Cámara","Cámara");tool("Prof. campo","DOF");tool("Calidad","Calidad");tool("Render","Render");tool("→ Creative","ExportCreative")
        root.addView(tools,LinearLayout.LayoutParams(-1,76));root.addView(canvas,LinearLayout.LayoutParams(-1,0,1f));root.addView(tv("Etapa 10: configuración interactiva de luces, sombras, ambiente/HDRI, cámara, profundidad de campo, calidad y previsualización de render.",13f),LinearLayout.LayoutParams(-1,72));setContentView(root)
    }
    private fun exportRenderToCreative(view: View){
        try {
            val bmp=Bitmap.createBitmap(view.width.coerceAtLeast(1),view.height.coerceAtLeast(1),Bitmap.Config.ARGB_8888)
            val canvas=Canvas(bmp); view.draw(canvas)
            val shared=File(filesDir,"alx_shared").apply{mkdirs()}
            val out=File(shared,"render-3d-${System.currentTimeMillis()}.png")
            FileOutputStream(out).use{bmp.compress(Bitmap.CompressFormat.PNG,100,it)}
            val intent=Intent(this,com.alx.creativestudio.MainActivity::class.java).apply{addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP);putExtra("render3DPath",out.absolutePath)}
            startActivity(intent)
        } catch(e:Exception){Toast.makeText(this,"No se pudo enviar el render a Creative",Toast.LENGTH_LONG).show()}
    }

    private fun showAnimationModule(){
        val root=LinearLayout(this).apply{orientation=LinearLayout.VERTICAL;setBackgroundColor(bg)}
        val bar=LinearLayout(this).apply{orientation=LinearLayout.HORIZONTAL;setPadding(10,10,10,10);setBackgroundColor(panel)}
        bar.addView(button("‹ Inicio"){showHome()},LinearLayout.LayoutParams(0,60,1f));bar.addView(tv("ANIMACIÓN 3D",18f,true),LinearLayout.LayoutParams(0,60,2f));root.addView(bar)
        val tools=HorizontalScrollView(this);val row=LinearLayout(this).apply{orientation=LinearLayout.HORIZONTAL;setPadding(8,8,8,8)};tools.addView(row);val canvas=AnimationView(this)
        fun tool(n:String,k:String){row.addView(button(n){canvas.applyTool(k)},LinearLayout.LayoutParams(155,58).apply{setMargins(4,0,4,0)})}
        tool("+ Keyframe","Key");tool("Posición","Pos");tool("Rotación","Rot");tool("Escala","Scale");tool("Cámara","Cam");tool("Personaje","Char");tool("Objeto","Obj");tool("Luz","Light");tool("Interpolación","Interp");tool("Recorrido","Path");tool("▶ Reproducir","Play")
        root.addView(tools,LinearLayout.LayoutParams(-1,76));root.addView(canvas,LinearLayout.LayoutParams(-1,0,1f));root.addView(tv("Etapa 11: timeline y keyframes iniciales para transformaciones, cámaras, personajes, objetos, luces, interpolación y recorridos arquitectónicos.",13f),LinearLayout.LayoutParams(-1,72));setContentView(root)
    }
    private fun showPresentationModule(){
        val root=LinearLayout(this).apply{orientation=LinearLayout.VERTICAL;setBackgroundColor(bg)}
        val bar=LinearLayout(this).apply{orientation=LinearLayout.HORIZONTAL;setPadding(10,10,10,10);setBackgroundColor(panel)}
        bar.addView(button("‹ Inicio"){showHome()},LinearLayout.LayoutParams(0,60,1f));bar.addView(tv("RECORRIDOS / AR-VR",18f,true),LinearLayout.LayoutParams(0,60,2f));root.addView(bar)
        val tools=HorizontalScrollView(this);val row=LinearLayout(this).apply{orientation=LinearLayout.HORIZONTAL;setPadding(8,8,8,8)};tools.addView(row);val canvas=PresentationView(this)
        fun tool(n:String,k:String){row.addView(button(n){canvas.applyTool(k)},LinearLayout.LayoutParams(165,58).apply{setMargins(4,0,4,0)})}
        tool("1ª persona","Walk");tool("Vista 360°","360");tool("Presentación","Present");tool("Preparar AR","AR");tool("Preparar VR","VR");tool("Reiniciar","Reset")
        root.addView(tools,LinearLayout.LayoutParams(-1,76));root.addView(canvas,LinearLayout.LayoutParams(-1,0,1f));root.addView(tv("Etapa 12: presentación interactiva con recorrido conceptual en primera persona, vista 360° y preparación de estados para futuras integraciones AR/VR nativas.",13f),LinearLayout.LayoutParams(-1,72));setContentView(root)
    }

    private fun showAssistantModule(){
        val root=LinearLayout(this).apply{orientation=LinearLayout.VERTICAL;setPadding(24,28,24,24);setBackgroundColor(bg)}
        val bar=LinearLayout(this).apply{orientation=LinearLayout.HORIZONTAL;setBackgroundColor(panel)}
        bar.addView(button("‹ Inicio"){showHome()},LinearLayout.LayoutParams(0,60,1f));bar.addView(tv("ASISTENTE ALX DE DISEÑO",18f,true),LinearLayout.LayoutParams(0,60,2f));root.addView(bar)
        val context=Spinner(this).apply{adapter=ArrayAdapter(this@MainActivity,android.R.layout.simple_spinner_dropdown_item,listOf("Modelado 3D","Arquitectura","Interiores","Materiales","Iluminación","Personajes","Cosplay"))}
        root.addView(context,LinearLayout.LayoutParams(-1,64))
        val answer=tv("Selecciona un área y solicita una guía contextual. El asistente no modifica el proyecto sin permiso.",16f)
        root.addView(answer,LinearLayout.LayoutParams(-1,0,1f))
        root.addView(button("Guía contextual"){val area=context.selectedItem.toString();answer.text=assistantGuide(area)},LinearLayout.LayoutParams(-1,68))
        root.addView(button("Diagnóstico del proyecto"){answer.text="Diagnóstico: revisa escala/unidades, objetos bloqueados, materiales sin UV, iluminación y guardado antes de exportar. Las correcciones se proponen; no se aplican automáticamente."},LinearLayout.LayoutParams(-1,68))
        root.addView(tv("Etapa 13: ayuda contextual para herramientas, medidas, materiales, distribución, iluminación, modelado, aprendizaje y solución de errores.",13f));setContentView(root)
    }
    private fun assistantGuide(area:String)=when(area){
        "Arquitectura"->"Arquitectura: define unidades y medidas reales, crea muros/niveles, coloca aberturas y verifica cotas antes de levantar la vista 3D."
        "Interiores"->"Interiores: establece circulación y escala del espacio; después mobiliario, materiales e iluminación por zonas."
        "Materiales"->"Materiales: parte de color y rugosidad; añade metalicidad/transparencia cuando corresponda y revisa UV antes de texturizar."
        "Iluminación"->"Iluminación: combina ambiente con luces principales, activa sombras y ajusta cámara/DOF antes de subir calidad de render."
        "Personajes"->"Personajes: trabaja primero proporciones y simetría; después rostro/cuerpo, ropa, accesorios, pose y rig."
        "Cosplay"->"Cosplay: escala al cuerpo, separa piezas, elige foam/papercraft/impresión 3D y revisa materiales antes de fabricar."
        else->"Modelado 3D: crea primitivas, transforma con precisión y usa extrusión, inset, bisel, subdivisión y booleanas de forma progresiva."
    }

    override fun onBackPressed(){showHome()}
}

data class Shape(var type:Int,var x:Float,var y:Float,var size:Float=110f,var rot:Float=0f,var hidden:Boolean=false,var locked:Boolean=false)
class StudioView(ctx:android.content.Context): View(ctx){
    val shapes=mutableListOf<Shape>(); var selected=-1; var mode=0; var zoom=1f; var material="MATE";var uv=false;var normal=false;var smooth=false;var booleanLabel=""; private var lastX=0f;private var lastY=0f; private val undo=java.util.Stack<List<Shape>>();private val redo=java.util.Stack<List<Shape>>();private var span=0f
    private val p=Paint(1); init{setBackgroundColor(Color.rgb(6,12,22)); addShape(0)}
    private fun snap()=shapes.map{it.copy()}; private fun checkpoint(){undo.push(snap());if(undo.size>30)undo.removeAt(0);redo.clear()}
    fun addShape(t:Int){checkpoint();shapes.add(Shape(t,220f+shapes.size*35,300f+shapes.size*25));selected=shapes.lastIndex;invalidate();autoSave()}
    fun duplicate(){if(selected>=0){checkpoint();val s=shapes[selected].copy(x=shapes[selected].x+45,y=shapes[selected].y+45);shapes.add(s);selected=shapes.lastIndex;invalidate();autoSave()}}
    fun toggleHidden(){if(selected>=0){checkpoint();shapes[selected].hidden=!shapes[selected].hidden;invalidate();autoSave()}}
    fun toggleLocked(){if(selected>=0){checkpoint();shapes[selected].locked=!shapes[selected].locked;invalidate();autoSave()}}
    fun extrude(){if(selected>=0){checkpoint();shapes[selected].size*=1.18f;invalidate();autoSave()}}; fun inset(){if(selected>=0){checkpoint();shapes[selected].size*=.88f;invalidate();autoSave()}}; fun bevel(){if(selected>=0){checkpoint();shapes[selected].rot+=8;invalidate();autoSave()}};fun subdivide(){smooth=true;invalidate()};fun mirror(){if(selected>=0){checkpoint();shapes[selected].x=width-shapes[selected].x;invalidate();autoSave()}}
    fun undo(){if(undo.isNotEmpty()){redo.push(snap());restore(undo.pop())}};fun redo(){if(redo.isNotEmpty()){undo.push(snap());restore(redo.pop())}};private fun restore(v:List<Shape>){shapes.clear();shapes.addAll(v.map{it.copy()});selected=shapes.lastIndex;invalidate();autoSave()}
    fun saveNow(){autoSave();Toast.makeText(context,"Proyecto guardado",Toast.LENGTH_SHORT).show()}; private fun autoSave(){runCatching{File(context.filesDir,"autosave.alx3d").writeText(shapes.joinToString("\n"){"${it.type},${it.x},${it.y},${it.size},${it.rot}"})}}
    override fun onDraw(c:Canvas){super.onDraw(c);c.save();c.scale(zoom,zoom);p.strokeWidth=1f;p.color=Color.rgb(28,55,76);for(x in 0..width step 45)c.drawLine(x.toFloat(),0f,x.toFloat(),height.toFloat(),p);for(y in 0..height step 45)c.drawLine(0f,y.toFloat(),width.toFloat(),y.toFloat(),p);p.strokeWidth=4f;p.color=Color.RED;c.drawLine(35f,height-55f,130f,height-55f,p);p.color=Color.GREEN;c.drawLine(35f,height-55f,35f,height-150f,p)
        shapes.forEachIndexed{i,s->if(!s.hidden){c.save();c.rotate(s.rot,s.x,s.y);p.style=Paint.Style.FILL;p.color=when(material){"METAL"->Color.LTGRAY;"VIDRIO"->Color.argb(150,80,200,255);"RUGOSO"->Color.rgb(110,125,140);else->Color.rgb(28,160,215)};when(s.type){1->c.drawCircle(s.x,s.y,s.size/2,p);2->c.drawRoundRect(s.x-s.size/3,s.y-s.size/2,s.x+s.size/3,s.y+s.size/2,35f,35f,p);else->c.drawRect(s.x-s.size/2,s.y-s.size/2,s.x+s.size/2,s.y+s.size/2,p)};if(i==selected){p.style=Paint.Style.STROKE;p.strokeWidth=5f;p.color=Color.rgb(31,210,255);c.drawCircle(s.x,s.y,s.size*.7f,p)};c.restore()}};c.restore();p.style=Paint.Style.FILL;p.color=Color.WHITE;p.textSize=28f;c.drawText("XYZ  •  Zoom ${"%.1f".format(zoom)}x  •  $material${if(uv)" • UV" else ""}${if(normal)" • NORMAL" else ""}${if(booleanLabel.isNotEmpty())" • $booleanLabel" else ""}",20f,38f,p)}
    override fun onTouchEvent(e:MotionEvent):Boolean{if(e.pointerCount==2){val dx=e.getX(0)-e.getX(1);val dy=e.getY(0)-e.getY(1);val ns=sqrt(dx*dx+dy*dy);if(span>0)zoom=(zoom*(ns/span)).coerceIn(.5f,2.5f);span=ns;invalidate();return true};span=0f;val x=e.x/zoom;val y=e.y/zoom;when(e.action){MotionEvent.ACTION_DOWN->{lastX=x;lastY=y;selected=shapes.indexOfLast{!it.hidden&&hypot(it.x-x,it.y-y)<it.size};if(selected>=0)checkpoint();invalidate()};MotionEvent.ACTION_MOVE->{if(selected>=0&&!shapes[selected].locked){val s=shapes[selected];val dx=x-lastX;val dy=y-lastY;when(mode){0->{s.x+=dx;s.y+=dy};1->s.rot+=dx*.5f;2->s.size=(s.size+dx).coerceIn(35f,350f)};lastX=x;lastY=y;invalidate()}};MotionEvent.ACTION_UP->autoSave()};return true}
}


class DesignView(ctx:android.content.Context,val module:Int):View(ctx){
    private val p=Paint(1);private val items=mutableListOf<String>();private var is3d=false
    init{setBackgroundColor(Color.rgb(6,12,22))}
    fun addItem(k:String){if(k=="3D")is3d=!is3d else items.add(k);invalidate();Toast.makeText(context,if(k=="3D") "Vista ${if(is3d) "3D conceptual" else "2D"}" else "$k añadido",Toast.LENGTH_SHORT).show()}
    override fun onDraw(c:Canvas){super.onDraw(c);p.strokeWidth=1f;p.color=Color.rgb(28,55,76);for(x in 0..width step 50)c.drawLine(x.toFloat(),0f,x.toFloat(),height.toFloat(),p);for(y in 0..height step 50)c.drawLine(0f,y.toFloat(),width.toFloat(),y.toFloat(),p);p.textSize=22f;p.color=Color.WHITE;c.drawText(if(is3d)"VISTA 3D CONCEPTUAL • unidades métricas" else "PLANO 2D • unidades métricas • cuadrícula 0.5 m",18f,34f,p);items.forEachIndexed{i,k->val col=i%3;val row=i/3;val x=45f+col*210;val y=100f+row*125;p.style=Paint.Style.FILL;p.color=Color.rgb(25,115+(i*13)%90,175);when(k){"Árbol","Planta"->c.drawCircle(x+55,y+35,35f,p);"Piscina"->c.drawOval(x,y,x+145,y+70,p);else->c.drawRoundRect(x,y,x+150,y+72,12f,12f,p)};p.color=Color.WHITE;p.textSize=18f;c.drawText(k,x+8,y+43,p)} }
}


class CharacterView(ctx:android.content.Context):View(ctx){
    private val p=Paint(1); private val parts=mutableListOf("Base humana"); private var symmetry=true; private var brush="Suavizar"; private var rig=false
    init{setBackgroundColor(Color.rgb(6,12,22))}
    fun applyTool(k:String){when(k){"Simetría"->symmetry=!symmetry;"Inflar","Suavizar"->brush=k;"Rig"->rig=!rig;else->parts.add(k)};invalidate();Toast.makeText(context,"$k aplicado",Toast.LENGTH_SHORT).show()}
    override fun onDraw(c:Canvas){super.onDraw(c);p.color=Color.WHITE;p.textSize=22f;c.drawText("PERSONAJE • ${if(symmetry) "SIMETRÍA ON" else "SIMETRÍA OFF"} • Pincel $brush",18f,34f,p);val cx=width/2f; p.color=Color.rgb(45,165,210);c.drawCircle(cx,150f,62f,p);c.drawRoundRect(cx-75,215f,cx+75,500f,55f,55f,p);c.drawRoundRect(cx-155,240f,cx-75,440f,35f,35f,p);c.drawRoundRect(cx+75,240f,cx+155,440f,35f,35f,p);c.drawRoundRect(cx-70,485f,cx-5,730f,30f,30f,p);c.drawRoundRect(cx+5,485f,cx+70,730f,30f,30f,p);if(rig){p.style=Paint.Style.STROKE;p.strokeWidth=5f;p.color=Color.YELLOW;c.drawLine(cx,150f,cx,600f,p);c.drawLine(cx,280f,cx-120,360f,p);c.drawLine(cx,280f,cx+120,360f,p);p.style=Paint.Style.FILL};p.color=Color.WHITE;p.textSize=18f;c.drawText("Componentes: "+parts.takeLast(5).joinToString(" • "),18f,height-35f,p)}
}
class CosplayView(ctx:android.content.Context):View(ctx){
    private val p=Paint(1);private val parts=mutableListOf<String>();private var scale=1.0f;private var mode="FOAM"
    init{setBackgroundColor(Color.rgb(6,12,22))}
    fun addPart(k:String){when(k){"Escala"->{scale=if(scale<1.2f) scale+.05f else .85f};"Foam"->mode="FOAM";"Papercraft"->mode="PAPERCRAFT";"3D Print"->mode="IMPRESIÓN 3D";"Materiales"->Toast.makeText(context,"Estimación: ${parts.size.coerceAtLeast(1)} piezas • escala ${"%.2f".format(scale)}",Toast.LENGTH_LONG).show();else->parts.add(k)};invalidate()}
    override fun onDraw(c:Canvas){super.onDraw(c);p.color=Color.WHITE;p.textSize=22f;c.drawText("FABRICACIÓN • $mode • escala ${"%.2f".format(scale)}",18f,34f,p);parts.forEachIndexed{i,k->val col=i%2;val row=i/2;val x=45f+col*330;val y=95f+row*120;p.color=Color.rgb(25,135+(i*11)%80,190);c.drawRoundRect(x,y,x+260,y+78,18f,18f,p);p.color=Color.WHITE;p.textSize=19f;c.drawText("${i+1}. $k",x+16,y+46,p)};p.textSize=17f;c.drawText("Piezas: ${parts.size} • preparación conceptual para separación y fabricación",18f,height-30f,p)}
}


class LightingView(ctx:android.content.Context):View(ctx){
 private val p=Paint(1);private val lights=mutableListOf<String>();private var shadows=true;private var env="ESTUDIO";private var dof=false;private var quality=1
 init{setBackgroundColor(Color.rgb(6,12,22))}
 fun applyTool(k:String){when(k){"Sol","Puntual","Área","Spot"->lights.add(k);"Sombras"->shadows=!shadows;"Ambiente"->env=if(env=="ESTUDIO")"EXTERIOR" else "ESTUDIO";"HDRI"->env="HDRI";"DOF"->dof=!dof;"Calidad"->quality=quality%3+1;"Render"->Toast.makeText(context,"Previsualización render Q$quality preparada",Toast.LENGTH_SHORT).show()};invalidate()}
 override fun onDraw(c:Canvas){super.onDraw(c);val cx=width/2f;val cy=height/2f;p.color=if(env=="HDRI")Color.rgb(38,65,92) else Color.rgb(18,34,55);c.drawRect(0f,0f,width.toFloat(),height.toFloat(),p);p.color=Color.rgb(40+lights.size*15,135,190);c.drawRoundRect(cx-170,cy-130,cx+170,cy+130,28f,28f,p);if(shadows){p.color=Color.argb(100,0,0,0);c.drawOval(cx-200,cy+120,cx+210,cy+205,p)};p.color=Color.WHITE;p.textSize=22f;c.drawText("LUCES ${lights.size} • $env • SOMBRAS ${if(shadows)"ON" else "OFF"} • DOF ${if(dof)"ON" else "OFF"} • Q$quality",18f,36f,p);lights.takeLast(5).forEachIndexed{i,l->c.drawText("• $l",25f,80f+i*30,p)}}
}
class AnimationView(ctx:android.content.Context):View(ctx){
 private val p=Paint(1);private val keys=mutableListOf<Pair<Int,String>>();private var frame=0;private var channel="Pos";private var interpolation="Lineal";private var playing=false
 init{setBackgroundColor(Color.rgb(6,12,22))}
 fun applyTool(k:String){when(k){"Key"->{keys.add(frame to channel);frame=(frame+10).coerceAtMost(100)};"Pos","Rot","Scale","Cam","Char","Obj","Light"->channel=k;"Interp"->interpolation=if(interpolation=="Lineal")"Suave" else "Lineal";"Path"->{channel="Recorrido";keys.add(frame to channel)};"Play"->playing=!playing};invalidate()}
 override fun onDraw(c:Canvas){super.onDraw(c);p.color=Color.WHITE;p.textSize=22f;c.drawText("TIMELINE • frame $frame • $channel • $interpolation • ${if(playing)"PLAY" else "PAUSA"}",18f,35f,p);val y=height-110f;p.strokeWidth=4f;p.color=Color.LTGRAY;c.drawLine(35f,y,width-35f,y,p);keys.forEach{(f,ch)->val x=35f+(width-70f)*f/100f;p.color=Color.rgb(31,210,255);c.drawCircle(x,y,11f,p);p.textSize=14f;c.drawText(ch,x-18,y-20,p)};p.color=Color.rgb(45,165,210);c.drawCircle(width/2f,260f,75f,p);p.color=Color.WHITE;p.textSize=18f;c.drawText("Keyframes: ${keys.size}",18f,72f,p)}
}
class PresentationView(ctx:android.content.Context):View(ctx){
 private val p=Paint(1);private var mode="PRESENTACIÓN";private var yaw=0f
 init{setBackgroundColor(Color.rgb(6,12,22))}
 fun applyTool(k:String){when(k){"Walk"->mode="PRIMERA PERSONA";"360"->{mode="VISTA 360°";yaw=(yaw+45)%360};"Present"->mode="PRESENTACIÓN";"AR"->mode="AR PREPARADO";"VR"->mode="VR PREPARADO";"Reset"->{mode="PRESENTACIÓN";yaw=0f}};invalidate()}
 override fun onDraw(c:Canvas){super.onDraw(c);val h=height.toFloat();p.color=Color.rgb(24,56,85);c.drawRect(0f,0f,width.toFloat(),h*.55f,p);p.color=Color.rgb(38,75,55);c.drawRect(0f,h*.55f,width.toFloat(),h,p);p.color=Color.rgb(185,190,195);c.drawRect(width*.25f,h*.32f,width*.75f,h*.7f,p);p.color=Color.rgb(70,105,135);c.drawRect(width*.43f,h*.48f,width*.57f,h*.7f,p);p.color=Color.WHITE;p.textSize=22f;c.drawText("$mode • orientación ${yaw.toInt()}°",18f,35f,p);p.textSize=17f;c.drawText("AR/VR: preparación de presentación; integración nativa futura",18f,h-28f,p)}
}
