# AIS-Practica-3-2023

Autores: Jiajie Ni y Gabriel Sánchez Losa

[Repositorio](https://github.com/gabrity98/ais-j.ni.2020-g.sanchezl.2020-2023)

[Aplicación Okteto](https://books-gabrity98.cloud.okteto.net/)

## Desarrollo con GitFlow

Una vez creados los workflows y funcionando estos, pasamos a crear la nueva funcionalidad utilizando Gitflow. Para ello, comenzamos clonando el repositorio para trabajar en él localmente.

```
$ git clone git@github.com:gabrity98/ais-j.ni.2020-g.sanchezl.2020-2023.git
```
Queremos desarrollar una nueva funcionalidad, por lo que el siguiente paso consiste en crear la rama *develop*, y a partir de ella una nueva rama *feature*, en la cual haremos las modificaciones deseadas.
```
$ git checkout -b develop
$ git checkout -b feature
```
Nos desplazamos a la carpeta donde se encuentra la clase que queremos modificar (BookDetail) para añadir la nueva funcionalidad.
```
$ cd src/main/java/es/codeurjc/ais/book
```
Y para modificar el archivo usaremos nano (también se podría hacer mediante un entorno de programación como Visual Studio).
```
$ nano BookDetail.java
```
La funcionalidad que queremos desarrollar consiste en un control de las descripciones de los libros, de tal forma que si supera los 950 caracteres sean acotadas a dicho rango y se añada "..." al final. Para ello, modificaremos la función *setDescription* con el siguiente código: Si la longitud de la descripción supera los 950 caracteres, se creará un string auxiliar en el que iremos almacenando los caracteres uno a uno hasta llegar al umbral de 950, tras lo cual añadiremos "..." al final.
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
Tras hacer los cambios deseados, hacemos un commit para dejar registrada la nueva mini-versión del proyecto.
```
$ git add BookDetail.java
$ git commit -m "feat: 950 characters description"
```
Ahora, hacemos push para que se vean reflejados los cambios en el repositorio online, lo cual hará que se ejecute automáticamente el [Workflow 1](https://github.com/gabrity98/ais-j.ni.2020-g.sanchezl.2020-2023/actions/runs/4891208923)
```
$ git push origin feature
```
Ahora que hemos terminado la nueva funcionalidad, cambiamos a la rama *develop*.
```
$ git checkout develop
```
Y hacemos push para poder hacer pull-request desde el repositorio online, lo cual hace que se ejecute automáticamente el [Workflow 2](https://github.com/gabrity98/ais-j.ni.2020-g.sanchezl.2020-2023/actions/runs/4891306938).
```
$ git push origin develop
```
Ahora podemos hacer un [pull-request](https://github.com/gabrity98/ais-j.ni.2020-g.sanchezl.2020-2023/pull/59) desde la rama *develop* para que se fusione con la rama *feature*. Esto ejecutará el [Workflow 1](https://github.com/gabrity98/ais-j.ni.2020-g.sanchezl.2020-2023/actions/runs/4891370430) y el [Workflow 2](https://github.com/gabrity98/ais-j.ni.2020-g.sanchezl.2020-2023/actions/runs/4891371902)

Posteriormente, hacemos pull desde el terminal para actualizar nuestro repositorio local y seguir trabajando.
```
git pull origin develop
```
A continuación, creamos la rama *release* partiendo de *develop*.
```
$ git checkout -b release
```
En esta fase del desarrollo debemos modificar los archivos pom.xml y docker-compose.yml.
```
$ nano pom.xml
```
Modificamos la versión del pom eliminando el sufijo "-SNAPSHOT" de la versión de la aplicación.
```
<groupId>es.codeurjc.ais</groupId>
<artifactId>practica_3_testing</artifactId>
<version>0.2.0</version>
```
De la misma forma, debemos modificar docker-compose.yml para que refleje esta nueva versión.
```
$ nano docker-compose.yml
```
```
services:
  books:
    image: gabrity98/books-reviewer:0.2.0
    ports:
      - 8080:8080
```
Volvemos a hacer commit.
```
$ git add pom.xml
$ git add docker-compose.yml
$ git commit -m "docs: removed -SNAPSHOT"
```
Y hacemos push, lo cual ejecutará automáticamente el [Workflow 4](https://github.com/gabrity98/ais-j.ni.2020-g.sanchezl.2020-2023/actions/runs/4891425297).
```
$ git push origin release
```
A continuación, volvemos a la rama develop.
```
$ git checkout develop
```
Y modificamos la versión de pom.xml a 0.3.0-SNAPSHOT para dejarla lista para la proxima versión.
```
$ nano pom.xml
```
```
<groupId>es.codeurjc.ais</groupId>
<artifactId>practica_3_testing</artifactId>
<version>0.3.0-SNAPSHOT</version>
```
Hacemos commit.
```
$ git add pom.xml
$ git commit -m "docs: changed version to 0.3.0-SNAPSHOT"
```
Y hacemos push, lo cual volverá a ejecutar el [Workflow 2](https://github.com/gabrity98/ais-j.ni.2020-g.sanchezl.2020-2023/actions/runs/4891448339).
```
$ git push origin develop
```
Por último, ya podemos hacer [pull request](https://github.com/gabrity98/ais-j.ni.2020-g.sanchezl.2020-2023/pull/60) desde la rama Master.
Este último push ejecutará automáticamente el [Workflow 5](https://github.com/gabrity98/ais-j.ni.2020-g.sanchezl.2020-2023/actions/runs/4891468806), publicará una [imagen en DockerHub](https://hub.docker.com/layers/gabrity98/books-reviewer/0.2.0/images/sha256:43699c3b75129031a7bfd9c7da2521c5aa58e92cb813baa227c81b956e42d229) y desplegará la aplicación en [Okteto](https://books-gabrity98.cloud.okteto.net/).

Para terminar, también contamos con un [Workflow 3](https://github.com/gabrity98/ais-j.ni.2020-g.sanchezl.2020-2023/actions/runs/4888826616) que se ejecuta automáticamente todas las noches a las 00:00 (horario UTC). Este workflow de Nightly ejecuta todos los tests en la rama *develop* y sube una [imagen a dockerhub](https://hub.docker.com/layers/gabrity98/books-reviewer/dev-20230505.010028/images/sha256:e89cb8fc8cad2806e0298fa5104d65e1badb33b11583fc78f6964991d4f2f743) con el tag "dev-*fecha*" 
