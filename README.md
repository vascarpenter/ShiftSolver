# 概要

optaplannerを使ったシフト割り振りプログラム

## 改善点

- [x] GUIをつけた
- [x] doctor名簿をCSVファイルから読み込む
- [x] doctor名簿をCSVファイルへ書き出す
- [x] doctorの拘束日・休日を GUIから設定
- [x] tableViewでの結果表示
- [x] 結果をCSVファイルで書き出す
- [x] optaplanner 7.0フォーマットの@ProblemFactPropertyを使用した
   - 6.0のgetProblemFactsは不要に。
   
## 課題
- [ ] doctorの拘束日・休日を optaplannerのルールに入れる
- [ ] jarで書き出すとうまく動かない
   - 原因はjar内のファイルはstreamとして読む必要があったから
   - doctor arrayの初期化ファイルをstream化、またcreateFromXmlInputStreamを使用した
   - しかしdrlをstream化できない DRLをListから読ませる方法教えて。
   