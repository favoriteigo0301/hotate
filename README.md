# hotate

# 概要
当リポジトリは、「Spring Boot」「vue.js」の技術検証をしながらサンプルパッケージを作成したのうち、本来の開発に入る予定です。
sample配下のパッケージはすべてサンプルコードとし、必ずしも正しいコードではありません。
技術調査の結果をコードとして記載しているので、お作法をためしている形になります。

# 開発環境
・端末：ubuntu（ローカル環境）  
・フロントエンド：「vue.js」「thymeleaf」  
・バックエンド：「Spring Boot」  
・マイグレーション：「flyway」  
・データベース：「mariaDB」  
・テスト：「Junit」「DBUnit(採用予定)」  

# 開発環境構築手順
1.```git clone```で最新ソースを取得  
2.build.gradleの```flyway{}```のデータベース設定をローカル用にする  
3.```gradle flywayMigrate```を叩き、マイグレーション実行する  
4.```gradle bootJar```を実行  
4.```gradle -jar /build/libs/study.jar```を叩き、localhost:8080/sample/memoにアクセスできればOK  

# テスト  
・```gradle test```を叩くとJunit