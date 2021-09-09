# Mutants

Magneto quiere reclutar la mayor cantidad de mutantes para poder luchar contra los X-Men. Te ha contratado a ti para que desarrolles un proyecto que detecte si un
humano es mutante basándose en su secuencia de ADN. En donde recibirás como parámetro un array de Strings que representan cada fila de una tabla
de (NxN) con la secuencia del ADN. Las letras de los Strings solo pueden ser: (A,T,C,G), las cuales representa cada base nitrogenada del ADN. Sabrás si un humano es mutante, si encuentras más de una secuencia de cuatro letras
iguales , de forma oblicua, horizontal o vertical.


# Api

## Diagrama de componentes

![Arquitectura](https://github.com/diazclaudia/Mutants/blob/master/img/componentes.png?raw=true)

## Explicación de la lógica del algoritmo implementado

Para la implementación del algoritmo de validación del genoma mutante se siguen los siguientes pasos:

1. Recorrido de la matriz en filas y por cada fila las columnas, en donde se valida si se contiene la secuencia de ADN.

![triangulo](https://github.com/diazclaudia/Mutants/blob/master/img/horizontal_vertical.png?raw=true)


2. Recorrido de la matriz en el triangulo inferior, en donde se hacen las validaciones a medida que se va recorriendo:

![triangulo](https://github.com/diazclaudia/Mutants/blob/master/img/lower.png?raw=true)


3. Recorrido de la matriz en el triangulo inferior inverso:

![triangulo](https://github.com/diazclaudia/Mutants/blob/master/img/lower_inverse.png?raw=true)


4. Recorrido de la matriz en el triangulo superior:

![triangulo](https://github.com/diazclaudia/Mutants/blob/master/img/upper.png?raw=true)

5. Recorrido de la matriz en el triangulo superior inverso:

![triangulo](https://github.com/diazclaudia/Mutants/blob/master/img/upper_inverse.png?raw=true)


# Ejecución del aplicativo

## Contenedor Docker

Pasos a seguir para ejecutar el contenedor Docker 

1. Tener previamente instalado maven en el equipo, para eso seguir este tutorial: https://www.baeldung.com/install-maven-on-windows-linux-mac
2. Tener previamente instalado Docker en el equipo
3. Una vez con el maven instalado ubicarse en la raíz del proyecto y ejecutar el comando `mvn clean install`
4. Luego ejecutar el comando `docker build -t "mutants-docker" .` 
5. Ejecutar el docker con el comando `docker run --name mutants -p 8080:8080 mutants-docker:latest`
6. Ahora al abrir la aplicación de Docker debe aparecer la imagen `mutants-docker` RUNNING y en el port 8080, por ejemplo: 

![Docker](https://github.com/diazclaudia/Mutants/blob/master/img/docker.png?raw=true)

8. Al abrir postman ya debe estar desplegado: ![Postman](https://github.com/diazclaudia/Mutants/blob/master/img/postman.png?raw=true)
9. Tener en cuenta que el puerto es el mismo que un puerto local, así que las ejecuciones de aplicativos locales deben estar apagadas.


## Despliegue en host

Se intentó desplegar en Heroku pero el proyecto al tener una base de datos embebida H2 no la soporta Heroku, por esta razón se usó Docker, así que dejo como deuda técnica implementar una base de datos externa ya sea web o local que sea compatible con un host en la nube.

![Docker](https://github.com/diazclaudia/Mutants/blob/master/img/heroku.png?raw=true)


## End Points Docker y locales

### /mutant

`curl --location --request POST 'http://localhost:8080/mutant' --header 'Content-Type: application/json' --header 'Cookie: JSESSIONID=46EECF48B646324BAA3F09D581E8FF6D' --data-raw '{
	"dna": [
        "ATGCGA",
        "CAGTGC",
        "TTATGT",
        "AGAAGG",
        "CCCCTA",
        "TCACGA"
    ]	
}'`

Response: En caso de mutante status 200, en caso de humano status 403

### /stats

`curl --location --request GET 'http://localhost:8080/stats' --header 'Cookie: JSESSIONID=46EECF48B646324BAA3F09D581E8FF6D'`

Response: `{
	"ration": 0.0,
	"count_mutant_dna": 1.0,
	"count_human_dna": 0.0
}`


### Pruebas unitarias y cobertura

![Docker](https://github.com/diazclaudia/Mutants/blob/master/img/test.png?raw=true)


