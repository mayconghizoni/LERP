LERP.exemplares = new Object();

$(document).ready(function ()  {

    LERP.exemplares.buscar = function(pagina, first){

        if (first){
            LERP.exemplares.montarNavegacao(pagina);
        }else{
            var itensPorPag = 9;
            var offset = (pagina * itensPorPag) - itensPorPag;

            $.ajax({
                type: "GET",
                url: "/LERP/rest/exemplar/buscarAtivos",
                data: "offset="+offset,
                success: function(dados){

                    if(dados == ""){
                        $("#listaExemplares").html("");
                    }else{
                        $("#listaExemplares").html(LERP.exemplares.exibir(dados));
                    }

                },
                error: function(info){
                    LERP.modalAviso("Erro ao buscar exemplares: "+info.status+" - " + info.statusText);
                }
            })
        }
    }

    LERP.exemplares.montarNavegacao = function(pagina){

        status = 1;

        $.ajax({
            type: "GET",
            url: "/LERP/rest/exemplar/buscar/"+status,
            success: function(listaExemplares){
                var itensPorPag = 9;
                var quantidadePaginas = Math.ceil(listaExemplares.length / itensPorPag);
                var paginacao = "";
                for (x = 0; x < quantidadePaginas; x ++){
                    paginacao += "<li class=\"page-item\" onclick='LERP.exemplares.buscar("+ (x + 1) +","+false+")'><a class=\"page-link\" href=\"#\">" + (x + 1) + "</a></li>"
                }
                $("#paginacao").html(paginacao);

                LERP.exemplares.buscar(pagina, false);
            },
            error: function(info){
                LERP.modalAviso("Erro ao buscar exemplares: "+info.status+" - " + info.statusText);
            }
        })

    }

    LERP.exemplares.buscar(1, true);

    LERP.exemplares.exibir = function(listaExemplares){

        if(listaExemplares != undefined && listaExemplares.length > 0 ) {

            var exemplar = "<div>";

            for (var i = 0; i < listaExemplares.length; i++) {

                exemplar += "<div class=\"card livros\">" +
                    "<div class=\"card-body\">" +
                    "<h5 class=\"card-title\">" + listaExemplares[i].titulo + "</h5>" +
                    "<p class=\"card-text\">" + listaExemplares[i].autor + "</p>" +
                    "<button type=\"button\" href=\"#\" class=\"btn btn-margin btn-outline-primary\" onclick=\"LERP.exemplares.buscarLeitores('"+listaExemplares[i].id+"','"+listaExemplares[i].titulo+"')\">Reserve agora</button>" +
                    "<button type=\"button\" href=\"#\" class=\"btn btn-margin btn-outline-secondary\" onclick=\"LERP.exemplares.ativarManutencao('"+listaExemplares[i].id+"')\">Manutenção</button>" +
                    "</div>" +
                    "</div>"
            }

            exemplar += "</div>";

        }

        return exemplar;

    }

    LERP.exemplares.buscarLeitores = function(id,nomeExemplar){

        $.ajax({
            type: "GET",
            url: "/LERP/rest/leitor/buscarAtivos",
            success: function(leitores) {

                if (leitores!=""){

                    //Cria opção escolha com valor vazio como padrão caso haja algo no banco de dados
                    $("#inputIdLeitor").html("")
                    var option = document.createElement("option")
                    option.setAttribute("value", "")
                    option.innerHTML = ("Escolha")
                    $("#inputIdLeitor").append(option)

                    //A cada valor encontrado no banco, cria mais uma option dentro do select
                    for (var i = 0; i < leitores.length; i++){

                        if(leitores[i].status == 1 || leitores[i].status == 2){
                            var option = document.createElement("option")
                            option.setAttribute("value", leitores[i].id)

                            option.innerHTML = (leitores[i].nome+" - "+leitores[i].id)
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

        LERP.exemplares.exibirCadastroEmprestimo(id, nomeExemplar);

    }

    LERP.exemplares.exibirCadastroEmprestimo = function(id, nomeExemplar){

        document.frmCadastroEmprestimo.idExemplar.value = nomeExemplar +" - "+ id;

        var modalManutencaoExemplar = {
            title: "Cadastrar novo empréstimo",
            height: 350,
            width: 550,
            buttons:{
                "Cadastrar": function(){

                    var emprestimo = new Object();

                    emprestimo.idLeitor = document.frmCadastroEmprestimo.inputIdLeitor.value;
                    emprestimo.idLivro = id;

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
                                LERP.modalAviso(msg);
                                LERP.exemplares.buscar(1);
                                $("#cadastraEmprestimo").trigger("reset");

                            },
                            error: function (info) {
                                LERP.modalAviso(info.responseText);
                            }
                        })
                        $(this).dialog("close");
                    }

                },
                "Cancelar": function(){
                    $(this).dialog("close");
                }
            },
            close: function(){
                $(this).dialog("close");
            }

        }

        $("#modalCadastroEmprestimo").dialog(modalManutencaoExemplar);

    }

    LERP.exemplares.ativarManutencao = function(id){

        let modalManutencaoExemplar = {
            title: "Manutenção de exemplar",
            height: 200,
            width: 550,
            buttons:{
                "Sim": function(){
                    $.ajax({
                        type: "PUT",
                        url: "/LERP/rest/exemplar/ativarManutencao/"+id,
                        success: function(msg){
                            LERP.modalAviso(msg);
                            LERP.exemplares.buscar(1, false );
                            $("#modalManutencao").dialog("close");
                        },
                        error: function(info){
                            LERP.modalAviso(info.responseText);
                            $("#modalManutencao").dialog("close");
                        }
                    })
                },
                "Cancelar": function(){
                    $(this).dialog("close");
                }
            },
            close: function(){
                $(this).dialog("close");
            }
        }

        $("#modalManutencao").dialog(modalManutencaoExemplar);

    }

    LERP.exemplares.buscarPorNome = function () {

        var valorBusca = $("#campoBuscarPorNome").val();

        console.log(valorBusca)

        $.ajax({
            type: "GET",
            url: "/LERP/rest/exemplar/buscarPorNome",
            data: "valorBusca="+valorBusca,
            success: function (dados) {

                $("#listaExemplares").html(LERP.exemplares.exibir(dados));

            },
            error: function (info) {
                LERP.modalAviso("Erro: "+ info.status + " - " + info.statusText)
            }



        })

    }
})


