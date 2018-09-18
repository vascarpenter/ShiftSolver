# 概要

optaplannerを使ったシフト割り振りプログラム

## 使っているライブラリ
- optaplanner-distribution-7.10.0.Final/binary/*.jar
- slf4j-api-1.7.25.jar, slf4j-simple-1.7.25.jar
- opencsv-4.2.jar
- commons-lang-2.6.jar
- このソフトウェアはApache License 2.0 で配布されたコードを含む。
- このソフトウェアはApache License 2.0に準じて自由に改変してかまいません

## 改善点

- [x] GUIをつけた
- [x] doctor名簿をCSVファイルから読み込む
- [x] doctor名簿をCSVファイルへ書き出す
- [x] doctorの拘束日・休日を GUIから設定
- [x] tableViewでの結果表示
- [x] 結果をCSVファイルで書き出す
- [x] optaplanner 7.0フォーマットの@ProblemFactPropertyを使用した
   - 6.0のgetProblemFactsは不要に。
- [x] doctorの拘束日・休日を optaplannerのルールに入れる
- [x] あるdoctorの拘束日であった場合のscoreを下げるルールに変更した
  (前もって他のdoctorに禁止日HashMapを持たせることにした)
- [x] Windows環境下では csvを読むときにutf8を認識していないため文字化けしていた
- [x] jarで書き出して動く手段をget
   - DRLをResourceから null pointerを出さずに読ませるには；
    http://gikoha.hatenablog.com/entry/2018/09/18/171430
    を参照し kie.confを書き換える必要あり。

## 課題
- [ ] 休日の実装 (年、月、日、曜日)
- [ ] 連勤をなるべく避ける
- [ ] 拘束日の翌日はなるべくいれない　（連勤よりも強い)
- [ ] 休日の割り振りはなるべく平均化する
- [ ] internationalization
