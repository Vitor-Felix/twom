# twom
Aqui um guia rápido para usar o git:

##1º - Instalar o git
  Baixar o git [aqui](https://git-scm.com/downloads), para Windows é o velho clique em avançar de sempre(eu acho hue).</br>
  Serão instalados o 'Git Bash' e o 'Git GUI':</br>
    O 'Git Bash' é o modo clássico do Git, abre um terminal com ambiente configurado.</br>
    O 'Git GUI' é uma interface gráfica bonitinha.
  
  É recomendável fazer umas configurações iniciais setando seu nome e email.</br> 
  Basta abrir o 'Guit Bash' e digitar os comandos:</br>
  `git config --global.user.name 'SeuNomeAquiEntreAsAspasSimples'`</br>
  `git config --global.user.eamil 'seu@email.aqui'`
    
##2º - Clonar o repositório
  Agora que o git está instalado, você tem de criar um pasta onde vai trabalhar com o projeto no seu PC.</br>
  Aqui eu vou seguir os passos com o 'Git Bash', mas imagino que com o 'Git GUI' deva até ser mais simples.
  
  Com o terminal (Guit Bash) aberto, é só navegar até a pasta em que você vai trabalhar, usando o clássico
  comando `cd nomeDaPata/` para abrir a pasta e o `cd ..` para sair da pasta atual.</br>
  
  Agora que está na pasta certa use o comando `git clone` para clonar o projeto na sua pasta atual:</br> 
  `git clone https://github.com/Vitor-Felix/twom.git`</br>
  Pronto você já tem os arquivos do projeto localmente.
  
##3º - Ciclo básico em que trabalharemos
  Vamos deixar bem simples e não usaremos mais do que os comandos:</br>
  `git add`, `git commit`, `git pull`, `git push` e em uma casualidade, o `git merge`.
  
  Se não me engano o Guit tem três estágios para você poder subir seu código para o repositório do trabalho final.</br>
  Quando você está editando um código aí, você está no **working directory** e quando terminar a edição, usa:<br>
  `git add arquivoEditado.tipo`, que vai passar o arquivo do working directory para a **staging area**
  
  
