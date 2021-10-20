# compasso-AvaliacaoFinal-duvidasAPI
### Instruções:

- Para execução do projeto como um todo é necessário executar a classe `DuvidasApplication.java`;
- A documentação feita no Swagger está localizada em `~AvaliacaoFinal/swagger` contendo os arquivos: `docApiDuvidas.yaml` e `docApiDuvidas.json`.


#### Configurações:

As configurações da JPA e do DataSource para o MySQL ou H2 estão localizadas em `~AvaliacaoFinal/src/main/resources/`, contendo os arquivos:

- `application.properties` , onde é possível definir o perfil a ser iniciado ao executar o projeto.
- `application-prod.properties`
- `application-dev.properties`
- `application-test.properties`

##### OBS:

Este é um projeto Maven, no qual foi utilizada apenas linguagem Java e os SGBDs MySQL e H2.
O banco de dados é gerado automaticamente ao executar o projeto.

- Nome do banco para o banco de produção`projetoDuvidas`;
- Usuário padrão para o MySQL: `root` | para o H2: `sa`;
- Senha padrão para o MySQL: `root` | para o H2: ` `;
