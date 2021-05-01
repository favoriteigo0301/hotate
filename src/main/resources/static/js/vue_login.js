new Vue({
    el:"#login",
    data: {
        userName: '',
        password: '',
        userNameErrorMessage: '',
        passwordErrorMessage: '',
        checkFlg: true
    },
    methods: {
        login:function (event) {
            if (!this.userName) {
                this.userNameErrorMessage = "ユーザ名を入力してください"
                this.checkFlg = false
            }
            if (!this.password) {
                this.passwordErrorMessage= "パスワードを入力してください"
                this.checkFlg = false
            }
            if (!this.checkFlg) {
                // form送信をキャンセルする
                event.preventDefault()
            }
        }, registration:function (event) {
            this.login()
        }
    }
})