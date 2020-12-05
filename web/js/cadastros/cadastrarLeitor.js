LERP.cadastros = new Object()

    cadastrarNovoLeitor = function () {
    var leitor = new Object();

    leitor.nome = document.frmVisualizaEdicao.inputNome.value;
    leitor.fone = document.frmVisualizaEdicao.inputFone.value;
    leitor.cpf = document.frmVisualizaEdicao.inputCpf.value;
    leitor.endereco = document.frmVisualizaEdicao.inputEndereco.value;
    leitor.email = document.frmVisualizaEdicao.inputEmail.value;

    var nome = leitor.nome
    var expRegNome = new RegExp("^[A-zÀ-ü]{3,}([ ]{1}[A-zÀ-ü]{2,})+$")

    var fone = leitor.fone
    var expRegFone = new RegExp("^[(]{1}[1-9]{2}[)]{1}[0-9]{4,5}[-]{1}[0-9]{4}$")

    var cpf = leitor.cpf
    var expRegCpf = new RegExp("^[0-9]{3}[.]{1}[0-9]{3}[.]{1}[0-9]{3}[-]{1}[0-9]{2}$")

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
    else if (leitor.endereco == "" || leitor.endereco==undefined){
        LERP.modalAviso("Preencha o campo Endereço.")
        document.frmVisualizaEdicao.inputEndereco.focus()
        return false
    }
    else{

        leitor.cpf = (leitor.cpf).replace('.', '').replace('.', '').replace('/', '').replace('-', '');
        leitor.fone = (leitor.fone).replace('(', '').replace(')', '').replace('-', '');

        $.ajax({
            type: "POST",
            url: "/LERP/rest/leitor/inserir",
            data: JSON.stringify(leitor),

            success: function (msg) {
                $("#addLeitor").trigger("reset");
                LERP.modalAviso(msg);

            },
            error: function (info) {
                LERP.modalAviso("Erro ao cadastrar leitor: "+info.status+" - " + info.statusText);
            }
        })
    }
}

LERP.cadastros.buscarLogado = function () {

    $.ajax({
        type: "GET",
        url: "/LERP/rest/usuario/buscarLogado",
        success: function (dados) {

            if (dados == "") {
                console.log("erro")
            } else {
                LERP.telasExibidas(dados)
            }

        },
        error: function (info) {
            LERP.modalAviso("Erro ao buscar usuário logado: " + info.status + " - " + info.statusText);
        }
    })

}

LERP.cadastros.buscarLogado()

LERP.telasExibidas = function(dados){
    if(dados.acesso != 1){
        $('#cadastrarUsuario').remove()
    }
}