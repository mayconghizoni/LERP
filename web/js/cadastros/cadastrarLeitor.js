LERP.cadastros = new Object()

    cadastrarNovoLeitor = function () {
    var leitor = new Object();

    leitor.nome = document.frmAddLeitor.inputNome.value;
    leitor.fone = document.frmAddLeitor.inputFone.value;
    leitor.cpf = document.frmAddLeitor.inputCpf.value;
    leitor.endereco = document.frmAddLeitor.inputEndereco.value;
    leitor.email = document.frmAddLeitor.inputEmail.value;

    var nome = leitor.nome
    var expRegNome = new RegExp("^[A-zÀ-ü]{3,}([ ]{1}[A-zÀ-ü]{2,})+$")

    var fone = leitor.fone
    var expRegFone = new RegExp("^[(]{1}[1-9]{2}[)]{1}[0-9]{4,5}[-]{1}[0-9]{4}$")

    var cpf = leitor.cpf
    var expRegCpf = new RegExp("^[0-9]{3}[.]{1}[0-9]{3}[.]{1}[0-9]{3}[-]{1}[0-9]{2}$")

    if(!expRegNome.test(nome)){
        LERP.modalAviso("Preencha o campo Nome.")
        document.frmAddLeitor.inputNome.focus()
        return false
    }
    else if (leitor.email == ""){
        alert("Preencha o campo Email.")
        document.frmAddLeitor.inputEmail.focus()
        return false
    }
    else if (!expRegFone.test(fone)){
        LERP.modalAviso("Preencha o campo Telefone.")
        document.frmAddLeitor.inputFone.focus()
        return false
    }
    else if (!expRegCpf.test(cpf)){
        LERP.modalAviso("Preencha o campo CPF.")
        document.frmAddLeitor.inputCpf.focus()
        return false
    }
    else if (leitor.endereco==""){
        LERP.modalAviso("Preencha o campo Endereço.")
        document.frmAddLeitor.inputEndereco.focus()
        return false
    }
    else{
        $.ajax({
            type: "POST",
            url: "/LERP/rest/leitor/inserir",
            data: JSON.stringify(leitor),

            success: function (msg) {
                $("#addExemplar").trigger("reset");
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