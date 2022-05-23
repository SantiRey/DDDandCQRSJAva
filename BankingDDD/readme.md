CQRS -> command Query responsability Segregation
Scaling, Performance, Simplicity

Query: retorna data; no actuliza nada (lectura)
Command: Actualiza insertar eliminar (retornar)

Messages types:

-> Commands -> exectu action
-> Events -> change observables (cambian el estado de aplicacion, verbo en pasado)
-> Queries -> 

Patron Mediator interface que describe una comunicacion entre objetos
-> Mediador es una interface 