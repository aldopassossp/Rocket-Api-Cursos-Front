## Sobre o desafio

Nesse desafio você desenvolverá o front end do desafio anterior usando Thymeleaf 

Sobre o desafio anterior: Nós realizamos a criação de uma API fictícia para uma empresa de cursos de programação, onde em um primeiro momento, você foi realizado um CRUD, para criação de cursos.

Neste desafio, você poderá se desafiar indo além e criando novas funcionalidades, tanto no front-end, como no back-end.

Você pode usar o layout abaixo:

[Edição de cursos | Figma](https://www.figma.com/community/file/1372619469197515580/edicao-de-cursos)

Você deve utilizar: 

- Thymeleaf
- Tailwind
- Spring Security

A aplicação deve conter as seguintes funcionalidades:

- Tela de cadastro de um novo curso
- Tela de listagem de todos os cursos
    
    Nessa tela, deve ser possível clicar no nome do curso, com isso o usuário será direcionado para a tela do curso específico
    
- Na tela do curso específico deve conter um botão de editar.
    - Ao clicar no botão, os campos deverão ficar disponíveis para edição.
    - Deve ser possível, editar e excluir o curso.
- Deve consumir a própria API desenvolvida no desafio anterior.
- Você deve criar uma nova funcionalidade na API, onde o curso irá ter um novo atributo: “Professor”

Só para relembrar: 

No desafio passado utilizamos os seguintes parâmetros: 

### Rotas e regras de negócio

Antes das rotas, vamos entender qual a estrutura (propriedades) que uma task deve ter:

- `id` - Identificador único de cada curso
- `name` - Nome do curso
- `category` - Categoria do curso
- `Active` - Define se o curso está ativo ou não
- `created_at` - Data de quando o curso foi criado.
- `updated_at` - Deve ser sempre alterado para a data de quando o curso for atualizada.

Rotas:

- `POST - /cursos`
    
    Deve ser possível criar um curso no banco de dados, enviando os campos `name` e `category` por meio do `body` da requisição.
    
    Ao criar um curso, os campos: `id`, `created_at`   e `updated_at` devem ser preenchidos automaticamente, conforme a orientação das propriedades acima.
    
- `GET - /cursos`
    
    Deve ser possível listar todas os cursos salvos no banco de dados.
    
    Também deve ser possível realizar uma busca, filtrando os cursos pelo `name` e `category`
    
- `PUT - /cursos/:id`
    
    Deve ser possível atualizar um curso pelo `id`.
    
    No `body` da requisição, deve receber somente o `name` e/ou `category` para serem atualizados.
    
    Se for enviado somente o `name`, significa que o `category` não pode ser atualizado e vice-versa. Além disso `active` for informado nessa rota, ele deverá ser ignorado, pois a rota que deverá fazer essa atualização, é a de patch.
    
- `DELETE - /cursos/:id`
    
    Deve ser possível remover um curso pelo `id`.
    
- `PATCH - /cursos/:id/active`
    
    Essa rota servirá para marcar se o curso está ativo ou não, ou seja, um toggle entre true or false.
    

Indo além

Algumas sugestões do que pode ser implementado:

- Caso você queira implementar ainda mais a aplicação, você pode criar novas funcionalidades: Como uma tela de cadastro de usuário junto com a rota do back end.
- Pode fazer com que seja possível os alunos se inscreverem no curso.
- Você pode fazer funcionalidades para que alunos se increvam no curso, e com base na quantidade de alunos inscritos, ao clicar para ver as informações do curso. Será exibido este parâmetro.
