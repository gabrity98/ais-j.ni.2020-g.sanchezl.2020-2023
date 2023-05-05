# AIS-Practica-3-2023

Autores: Jiajie Ni y Gabriel Sánchez Losa

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
Ahora hacemos un push para que se vean reflejados los cambios en el repositorio online, lo cual hará que se ejecute automáticamente el [Workflow 1](https://github.com/gabrity98/ais-j.ni.2020-g.sanchezl.2020-2023/actions/runs/4891208923)
```
$ git push origin feature
```
Tras terminar la nueva funcionalidad, cambiamos a la rama develop.
```
$ git checkout develop
```
Y ahora hacemos push para poder hacer pull-request desde el repositorio online, lo cual hace que se ejecute automáticamente el [Workflow 2](https://github.com/gabrity98/ais-j.ni.2020-g.sanchezl.2020-2023/actions/runs/4891306938).
```
$ git push origin develop
```
Ahora podemos hacer un [pull-request](https://github.com/gabrity98/ais-j.ni.2020-g.sanchezl.2020-2023/pull/59) desde la rama develop para que se fusione con la rama feature y se registre la nueva funcionalidad. Esto ejecutará el [Workflow 1](https://github.com/gabrity98/ais-j.ni.2020-g.sanchezl.2020-2023/actions/runs/4891370430) y el [Workflow 2](https://github.com/gabrity98/ais-j.ni.2020-g.sanchezl.2020-2023/actions/runs/4891371902)

Ahora hacemos un pull desde el terminal para actualizar nuestro repositorio local.
```
git pull origin develop
```
Ahora creamos la rama release a partir de develop.
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
Nos preparamos para hacer un commit.
```
$ git add pom.xml
$ git add docker-compose.yml
```
```
$ git commit -m "docs: removed -SNAPSHOT"
```
Hacemos push, lo cual ejecutará automáticamente el [Workflow 4](https://github.com/gabrity98/ais-j.ni.2020-g.sanchezl.2020-2023/actions/runs/4891425297)
```
$ git push origin release
```
A continuación, volvemos a la rama develop.
```
$ git checkout develop
```
Y modificamos la versión de pom.xml a 0.3.0-SNAPSHOT para dejarla lista para la versión futura.
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
Y hacemos push, lo cual volverá a ejecutar el [Workflow 2](https://github.com/gabrity98/ais-j.ni.2020-g.sanchezl.2020-2023/actions/runs/4891448339)
```
$ git push origin develop
```
Por último, ya podemos hacer [pull request](https://github.com/gabrity98/ais-j.ni.2020-g.sanchezl.2020-2023/pull/60) desde la rama Master.
Este último push ejecutaría automáticamente el [Workflow 5](https://github.com/gabrity98/ais-j.ni.2020-g.sanchezl.2020-2023/actions/runs/4891468806), y también publicará una [imagen en DockerHub](https://hub.docker.com/layers/gabrity98/books-reviewer/0.2.0/images/sha256:43699c3b75129031a7bfd9c7da2521c5aa58e92cb813baa227c81b956e42d229).

Por último, tenemos el [Workflow 3](https://github.com/gabrity98/ais-j.ni.2020-g.sanchezl.2020-2023/actions/runs/4888826616) que se ejecuta automáticamente todas las noches a las 00:00 (horario UTC) que sube una [imagen a dockerhub](https://hub.docker.com/layers/gabrity98/books-reviewer/dev-20230505.010028/images/sha256:e89cb8fc8cad2806e0298fa5104d65e1badb33b11583fc78f6964991d4f2f743) con la fecha y hora en el tag.
