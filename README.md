# Aplicacion de Stock
Mi programa de stock hecho en java con base de datos SQL. Realizado en pasantias de 7mo año en 2022.


# Como compilarlo
Hace poco realice una prueba de compilacion con VS Code ya que no tengo NetBeans y hace mucho que no lo utilizo.
Con los siguientes pasos se puede compilar el programa en VS Code para probar su funcionamiento.

1. Instalar Java openJDK (por ejemplo Adoptium Temurin)
2. Chequear con "java -version" y "javac -version" que esten instaladas correctamente
3. Instalar la extesion "Java Extension Pack" de VS Code
4. Abrir la carpeta raiz del proyecto en VS Code
5. Una vez dentro de la carpeta compilamos los archivos .java en la carpeta bin (este paso ya esta hecho, pero es recomendado hacer si se hizo alguna modificacion al codigo)
con el comando "javac -d bin src/tiendatorresi/*.java"
6. Ahora para correr el programa usamos "javac -cp "bin;dist/TiendaTorresi.jar"  tiendatorresi.Inicio"

# A tener en cuenta
El script de la base de datos esta en la carpeta raiz y en mi caso yo utilice SQL Workbench 8.0
La base de datos se tiene que llamar tiendatorressi (en este caso se crea y se selecciona manualmente un schema)
El SQL server debe tener el nombre root y contraseña root para que funcione (o se puede cambiar del codigo)

Este es uno de mis primeros programas y me quedo un poco confuso, esta descontinuado y un poco confuso pero aun asi me parecio buena idea tenerlo guardado
