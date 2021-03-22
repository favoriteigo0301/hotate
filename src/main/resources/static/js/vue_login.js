new Vue({
    el:"#login",
    data: {
        userName: '',
        password: '',
        userNameErrorMessage: '',
        passwordErrorMessage: '',
        actionFlg: '',
        checkFlg: true
    },
    methods: {
        login:function (event) {
            this.actionFlg = 'login'
            if (!this.userName) {
                this.userNameErrorMessage = "タイトルを入力してください"
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
            this.actionFlg = 'registration'
            this.login()
        }
    }
})