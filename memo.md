### JavaCC

- LL(k) 文法を対象とする
  - 標準では LL(1)

[golo-lang][1] を参考に色々調べて記述。

### javaccPlugin

[javaccPlugin][2] は JavaCC を 実行するための Gradle plugin です。
Gradle のタスクとして JavaCC による処理が行えるので便利です。

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

#### VISITOR option の追加

`jjt` ファイルの `options` に `VISITOR = true;` を追加する。

```
options {
    VISITOR = true;
}

```
`Node` インタフェースに `jjtAccept` メソッドが定義される。

```java
public Object jjtAccept(ParserVisitor visitor, Object data);
```

対応した `ParseVisitor` インタフェースも定義される。

```java
public interface ParserVisitor
{
  public Object visit(SimpleNode node, Object data);
}
```

`ParserVisior` を実装したクラスを使用すれば、簡単に Visitor パターンで処理ができる。

```java
public class Parser {

    public static class SampleVisitor implements ParserVisitor {
        @Override
        public Object visit(SimpleNode node, Object data) {
            System.out.println(node.toString());
            node.childrenAccept(this, data);
            return data;
        }
    }

    public static void main(String[] args) throws Exception {
        Parser parser = new Parser(System.in);
        SimpleNode node = parser.start();
        node.jjtAccept(new SampleVisitor(), null);
    }

}
```

#### MULTI option の追加

`jjt` ファイルの `options` に `MULTI = true;` を追加する。

```
options {
    VISITOR = true;
    MULTI = true;
}
```

MULTI オプションを true にすると、各生成規則に対応した Node クラスが作成される。

- SimpleNode (これまでも存在したデフォルトの Node 実装クラス)
- ASTStart
- ASTExpression
- ASTTerm
- ASTFactor

また `ParseVisitor` に各ノードに対応する visit メソッドが定義される。

```java
public interface ParserVisitor
{
  public Object visit(SimpleNode node, Object data);
  public Object visit(ASTStart node, Object data);
  public Object visit(ASTExpression node, Object data);
  public Object visit(ASTTerm node, Object data);
  public Object visit(ASTFactor node, Object data);
}
```

- 減算と除算を定義する
- post にまとめる

[1]: https://github.com/eclipse/golo-lang/ "golo-lang"
[2]: https://github.com/johnmartel/javaccPlugin "javaccPlugin"
