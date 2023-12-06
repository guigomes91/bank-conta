# API para controle de conta bancária

- Spring Boot 3.1.5
- Java 17
- Spring JPA
- Validation do Hibernate
- PostgreSQL
- Lombok
- Spring Web
- Maven

Este serviço é responsável pelo gerenciamento de clientes e suas contas, tais como conta corrente e conta poupança. O serviço realiza o cadastro de clientes, contas e
realiza consulta de extratos, aplicação na poupança, retirada de dinheiro da conta corrente, visualização de saldos e muito mais.
Após realizar o cadastro de uma conta para o cliente, o serviço publica uma mensagem para o RabbitMQ na exchange *gomes-bank-email* para queue *notifications*. O serviço 
bank-notificacoes vai se inscrever nessa fila para ouvir o evento e disparar um email para o cliente desejando boas vindas ao banco.

