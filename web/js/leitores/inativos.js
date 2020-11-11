LERP.leitores = new Object();

$(document).ready(function () {

    LERP.leitores.buscar = function (pagina, first) {

        if (first){
            LERP.leitores.montarNavegacao(pagina);
        }else{
            var itensPorPag = 9;
            var offset = (pagina * itensPorPag) - itensPorPag;

            $.ajax({
                type: "GET",
                url: "/LERP/rest/leitor/buscarInativos",
                data: "offset="+offset,
                success: function (dados) {

                    if (dados == "") {
                        $("#listaLeitores").html("");
                    } else {
                        $("#listaLeitores").html(LERP.leitores.exibir(dados));
                    }

                },
                error: function (info) {
                    LERP.modalAviso("Erro ao buscar leitores: " + info.status + " - " + info.statusText);
                }
            })
        }


    }

    LERP.leitores.montarNavegacao = function(pagina){

        status = 3;

        $.ajax({
            type: "GET",
            url: "/LERP/rest/leitor/buscar/"+status,
            success: function(lista){
                var itensPorPag = 9;
                var quantidadePaginas = Math.ceil(lista.length / itensPorPag);
                var paginacao = "";
                for (x = 0; x < quantidadePaginas; x ++){
                    paginacao += "<li class=\"page-item\" onclick='LERP.leitores.buscar("+ (x + 1) +","+false+")'><a class=\"page-link\" href=\"#\">" + (x + 1) + "</a></li>"
                }
                $("#paginacao").html(paginacao);

                LERP.leitores.buscar(pagina, false);
            },
            error: function(info){
                LERP.modalAviso("Erro ao buscar leitores: "+info.status+" - " + info.statusText);
            }
        })

    }

    LERP.leitores.exibir = function(listaLeitores) {

        if(listaLeitores != undefined && listaLeitores.length > 0 ) {

            var leitor = "<div>";

            for (var i = 0; i < listaLeitores.length; i++) {
                    leitor += "<div class=\"card livros\">" +
                        "<div class=\"card-body\">" +
                        "<h5 class=\"card-title\">" + listaLeitores[i].nome + "</h5>" +
                        "<p class=\"card-text\">" + listaLeitores[i].email + "</p>" +
                        "<p class=\"card-text\">" + listaLeitores[i].fone + "</p>" +
                        "<button type=\"button\" href=\"#\" class=\"btn btn-margin btn-outline-primary\" onclick=\"LERP.leitores.visualizar('"+listaLeitores[i].id+"')\">Visualizar</button>" +
                        "<button type=\"button\" href=\"#\" class=\"btn btn-margin btn-outline-secondary\" onclick=\"LERP.leitores.ativar('"+listaLeitores[i].id+"')\">Ativar Leitor</button>" +
                        "</div>" +
                        "</div>"
            }

            leitor += "</div>";

        }

        return leitor;

    }

    LERP.leitores.buscar(1, true);

    LERP.leitores.visualizar = function (id) {

        $.ajax({
            type: "GET",
            url: "/LERP/rest/leitor/buscarPorId",
            data: "id="+id,
            success: function(leitor) {

                document.frmVisualizaEdicao.nome.value = leitor.nome
                document.frmVisualizaEdicao.id.value = leitor.id
                document.frmVisualizaEdicao.email.value = leitor.email
                document.frmVisualizaEdicao.cpf.value = leitor.cpf
                document.frmVisualizaEdicao.fone.value = leitor.fone
                document.frmVisualizaEdicao.endereco.value = leitor.endereco
                document.frmVisualizaEdicao.status.value = leitor.status

                var modalVisualizarEdicao = {
                    title: "Visualizar Leitor",
                    height: 500,
                    width: 500,
                    buttons:{
                        "Salvar alterações" : function () {
                            LERP.leitores.editar()
                            $(this).dialog("close")
                        },
                        "Fechar": function(){
                            $(this).dialog("close");
                        }
                    },
                    close: function(){
                        //caso o usuário simplesmente feche a caixa de edição não acontece nada.
                    }
                }

                $("#modalVisualizarEdicao").dialog(modalVisualizarEdicao);

            },
            error: function (info) {
                LERP.modalAviso("Erro ao buscar leitor: " + info.status + " - " + info.statusText);
            }
        })

    }

    LERP.leitores.editar = function () {

        var leitor = new Object()

        leitor.id = document.frmVisualizaEdicao.id.value
        leitor.nome = document.frmVisualizaEdicao.nome.value
        leitor.cpf = document.frmVisualizaEdicao.cpf.value
        leitor.fone = document.frmVisualizaEdicao.fone.value
        leitor.endereco = document.frmVisualizaEdicao.endereco.value
        leitor.email = document.frmVisualizaEdicao.email.value
        leitor.status = document.frmVisualizaEdicao.status.value

        var nome = leitor.nome
        var expRegNome = new RegExp("^[A-zÀ-ü]{3,}([ ]{1}[A-zÀ-ü]{2,})+$")

        var fone = leitor.fone
        var expRegFone = new RegExp("^[(]{1}[1-9]{2}[)]{1}[0-9]{4,5}[-]{1}[0-9]{4}$")

        var cpf = leitor.cpf
        var expRegCpf = new RegExp("^[0-9]{3}[.]{1}[0-9]{3}[.]{1}[0-9]{3}[-]{1}[0-9]{2}$")

        var endereco = leitor.endereco
        var expRegEndereco = new RegExp("^[A-zÀ-ü]{3,}[,]{1}[ ]{1}[1-9]{1,}$");

        var email = leitor.email;
        var expRegEmail = new RegExp("^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$")

        if(!expRegNome.test(nome)){
            LERP.modalAviso("Preencha o campo Nome.")
            document.frmVisualizaEdicao.inputNome.focus()
            return false
        }
        else if (!expRegEmail.test(email)){
            LERP.modalAviso("Preencha o campo Email.")
            document.frmVisualizaEdicao.inputEmail.focus()
            return false
        }
        else if (!expRegFone.test(fone)){
            LERP.modalAviso("Preencha o campo Telefone.")
            document.frmVisualizaEdicao.inputFone.focus()
            return false
        }
        else if (!expRegCpf.test(cpf)){
            LERP.modalAviso("Preencha o campo CPF.")
            document.frmVisualizaEdicao.inputCpf.focus()
            return false
        }
        else if (!expRegEndereco.test(endereco)){
            LERP.modalAviso("Preencha o campo Endereço.")
            document.frmVisualizaEdicao.inputEndereco.focus()
            return false
        }
        else{

            leitor.cpf = (leitor.cpf).replace('.', '').replace('.', '').replace('/', '').replace('-', '');
            leitor.fone = (leitor.fone).replace('(', '').replace(')', '').replace('-', '');

            $.ajax({
                type: "PUT",
                url: "/LERP/rest/leitor/alterar",
                data: JSON.stringify(leitor),
                success: function (msg) {
                    LERP.leitores.buscar()
                    LERP.modalAviso(msg);
                    $("#editaLeitor").trigger("reset");

                },
                error: function (info) {
                    LERP.modalAviso("Erro ao alterar leitor: "+info.status+" - " + info.statusText);
                }
            })

        }

    }

    LERP.leitores.ativar = function (id) {

        let modalAtivarLeitor = {
            title: "Ativar Leitor",
            height: 200,
            width: 550,
            buttons:{
                "Sim": function () {

                    $.ajax({
                        type: "PUT",
                        url: "/LERP/rest/leitor/ativar/"+id,
                        success: function (msg) {
                            LERP.modalAviso(msg);
                            $("#modalAtivarLeitor").dialog("close");
                            LERP.leitores.buscar();
                        },
                        error: function (info) {
                            LERP.modalAviso(info.responseText);
                            $("#modalAtivarLeitor").dialog("close");
                        }
                    })

                },
                "Cancelar": function () {
                    $(this).dialog("close");
                }
            },
            close: function(){
                $(this).dialog("close");
            }
        }

        $("#modalAtivarLeitor").dialog(modalAtivarLeitor);

    }

    LERP.leitores.buscarPorNome = function () {

        var valorBusca = $("#campoBuscarPorNome").val();

        console.log(valorBusca)

        $.ajax({
            type: "GET",
            url: "/LERP/rest/leitor/buscarPorNome",
            data: "va" +
                "lorBusca="+valorBusca,
            success: function (dados) {

                $("#listaLeitores").html(LERP.leitores.exibir(dados));

            },
            error: function (info) {
                LERP.modalAviso("Erro: "+ info.status + " - " + info.statusText)
            }


        })

    }

})