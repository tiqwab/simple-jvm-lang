### JavaCC

- LL(k) 文法を対象とする
  - 標準では LL(1)

### 構文規則

`.jj` ファイルに記述。

構文規則に epsilon は使用できない。
代わりに正規文法が使用できるので、それで表現する。

```
E -> T ("+" T | "-" T)*
T -> F ("*" T | "/" F)*
F -> N
```

構文規則の書き方は dragon book の構文主導翻訳スキームに則ったものになっている (と思う)。

### jjtree

jjtree を作成するために最低限必要な操作は以下の通り。

- 上で作成した `.jj` ファイルを `.jjt` ファイルに rename
- 開始規則に当たるメソッドの返り値を `SimpleNode` とする

開始規則は例えば以下のようにする。

- (1) `SimpleNode` は `Node` インタフェースの実装クラスで JavaCC により自動的に生成される
- (2) `jjtThis` で処理中の Node にアクセスできる

```
// (1)
SimpleNode start(): {
} {
    expression() <NEWLINE> {
        // (2) 
        return jjtThis;
    }
}
```

作成した Parser が生成する抽象構文木 (AST) の概形は `dump` メソッドにより確認できる。

```java
    public static void main(String[] args) throws Exception {
        Parser parser = new Parser(System.in);
        SimpleNode node = parser.start();
        node.dump("");
    }
```

```
> (1 + 2) * 3 - 5
start
 expression
  term
   factor
    expression
     term
      factor
     term
      factor
   factor
  term
   factor
```

作成される AST を使いやすくするためにいくつか変更を加える。
