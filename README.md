# AIS-Practica-3-2023

Autor(es): Jiajie Ni y Gabriel Sánchez Losa

[Repositorio](https://github.com/gabrity98/ais-j.ni.2020-g.sanchezl.2020-2023)

[Aplicación Okteto](https://books-gabrity98.cloud.okteto.net/)

## Desarrollo con GitFlow

Una vez creados los workflows y funcionando estos, pasamos a crear la nueva funcionalidad utilizando Gitflow:

```
$ git clone git@github.com:gabrity98/ais-j.ni.2020-g.sanchezl.2020-2023.git
```
A continuación, cremos la rama develop partiendo de la rama máster y cambiamos de rama para trabajar en ella.
```
$ git checkout -b develop
```
Y ahora, creamos la rama feature para desarrollar la nueva funcionalidad.
```
$ git checkout -b feature
```
Nos desplazamos a la carpeta donde se encuentra la clase que queremos modificar (BookDetail) para añadir la nueva funcionalidad
```
$ cd src/main/java/es/codeurjc/ais/book
```
Y para modificar el archivo usamos nano.
```
$ nano BookDetail.java
```
Y modificamos la función setDescription con el siguiente código, de tal forma que cuando la descripción supere los 950 caracteres, vayamos almacenando caracter a caracter la descripción en un string auxiliar hasta que llegue a dicho rango, tras lo cual mostrará '...'.
```
 public void setDescription(String description) {
        if (description.length() > 950) {
                String aux = "";
                for (int i=0; i<=950;i++) {
                        aux+= description.charAt(i);
                }
                description = aux + "...";
        }
        this.description = description;
    }
```
Tras hacer los cambios deseados, nos preparamos para hacer un commit.
```
$ git add BookDetail.java
```
Y hacemos un commit para registrar la nueva mini-versión del proyecto.
```
$ git commit -m "feat: 950 characters description"
```

