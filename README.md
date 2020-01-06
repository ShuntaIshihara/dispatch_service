# dispatch_service
test/  
テスト用の入力ファイルが存在するディレクトリ  
result/  
実行結果の出力ファイルが存在するディレクトリ   
generator/  
テストケースを作成するファイルが存在するディレクトリ   
テストケースの作り方  
~~~
$ cd generator/
$ make  
$ ./generator [filename] [casetype] [seed]  
~~~  
filenameは.txtのファイル形式でないと作れない。  
casetypeはテストケースのタイプを決めるものである。
casetypeが1の場合は頂点の数が200から400の間でランダムに選ばれる。
casetypeが2の場合は頂点の数が400になる。  
例  
~~~
$ cd generator/
$ make
$ ./generator testcase1.txt 1 1
~~~  
