LERP.emprestimos = new Object();

$(document).ready(function () {

    LERP.emprestimos.buscarExemplares = function(){

        $.ajax({
            type: "GET",
            url: "/LERP/rest/exemplar/buscar/1",
            success: function(exemplares) {

                if (exemplares!=""){

                    //Cria opção escolha com valor vazio como padrão caso haja algo no banco de dados
                    $("#inputIdLivro").html("")
                    var option = document.createElement("option")
                    option.setAttribute("value", "")
                    option.innerHTML = ("Selecione o livro desejado")
                    $("#inputIdLivro").append(option)

                    //A cada valor encontrado no banco, cria mais uma option dentro do select
                    for (var i = 0; i < exemplares.length; i++){

                        if(exemplares[i].status == 1){
                            var option = document.createElement("option")
                            option.setAttribute("value", exemplares[i].id)

                            option.innerHTML = (exemplares[i].titulo+" - ID: #"+exemplares[i].id)
                            $("#inputIdLivro").append(option)
                        }

                    }

                }else{
                    $("#inputIdLivro").html("")

                    //Caso o não tenha nenhum valor cadastrado no banco ele cria uma uma option com aviso!
                    var option = document.createElement("option")
                    option.setAttribute("value", "")
                    option.innerHTML = ("Cadastre um livro primeiro!")
                    $("#inputIdLivro").append(option)
                    $("#inputIdLivro").addClass("aviso")

                }
            },
            error: function (info) {
                LERP.modalAviso("Erro ao buscar livros: "+info.status+" - " + info.statusText)

                $("#inputIdLivro").html("")
                var option = document.createElement("option")
                option.setAttribute("value", "")
                option.innerHTML = ("Erro ao carregar livros!")
                $("#inputIdLivro").append(option)
                $("#inputIdLivro").addClass("aviso")
            }

        })

    }

    LERP.emprestimos.buscarExemplares()


    LERP.emprestimos.buscarLeitores = function(){

        $.ajax({
            type: "GET",
            url: "/LERP/rest/leitor/buscar/1",
            success: function(leitores) {

                if (leitores!=""){

                    //Cria opção escolha com valor vazio como padrão caso haja algo no banco de dados
                    $("#inputIdLeitor").html("")
                    var option = document.createElement("option")
                    option.setAttribute("value", "")
                    option.innerHTML = ("Selecione o leitor desejado")
                    $("#inputIdLeitor").append(option)

                    //A cada valor encontrado no banco, cria mais uma option dentro do select
                    for (var i = 0; i < leitores.length; i++){

                        if(leitores[i].status == 1 || leitores[i].status == 2){
                            var option = document.createElement("option")
                            option.setAttribute("value", leitores[i].id)

                            option.innerHTML = (leitores[i].nome+" - ID: #"+leitores[i].id)
                            $("#inputIdLeitor").append(option)
                        }

                    }

                }else{
                    $("#inputIdLeitor").html("")

                    //Caso o não tenha nenhum valor cadastrado no banco ele cria uma uma option com aviso!
                    var option = document.createElement("option")
                    option.setAttribute("value", "")
                    option.innerHTML = ("Cadastre um leitor primeiro!")
                    $("#inputIdLeitor").append(option)
                    $("#inputIdLeitor").addClass("aviso")

                }
            },
            error: function (info) {
                LERP.modalAviso("Erro ao buscar leitores: "+info.status+" - " + info.statusText)

                $("#inputIdLeitor").html("")
                var option = document.createElement("option")
                option.setAttribute("value", "")
                option.innerHTML = ("Erro ao carregar leitores!")
                $("#inputIdLeitor").append(option)
                $("#inputIdLeitor").addClass("aviso")
            }

        })

    }

    LERP.emprestimos.buscarLeitores();


    LERP.emprestimos.cadastrarEmprestimo = function () {

        var emprestimo = new Object();

        emprestimo.idLeitor = document.frmAddEmprestimo.inputIdLeitor.value;
        emprestimo.idLivro = document.frmAddEmprestimo.inputIdLivro.value;

        if(emprestimo.idLeitor == ""){
            LERP.modalAviso("Preencha o campo com o ID do leitor.")
            document.frmAddEmprestimo.inputIdLeitor.focus()
            return false
        }
        else if(emprestimo.idLivro == ""){
            LERP.modalAviso("Preencha o campo com o ID do livro.")
            document.frmAddEmprestimo.inputIdLivro.focus()
        }
        else{

            $.ajax({
                type: "POST",
                url: "/LERP/rest/emprestimo/inserir",
                data: JSON.stringify(emprestimo),

                success: function (msg) {
                    $("#addEmprestimo").trigger("reset");
                    LERP.modalAviso(msg);
                    LERP.emprestimos.buscarExemplares()
                    LERP.emprestimos.buscarLeitores()
                },
                error: function (info) {
                    LERP.modalAviso("Erro ao realizar empréstimo: "+info.statusText+" - " + info.status);
                }
            })

        }

    }

})