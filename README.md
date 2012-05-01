anubis - a NEW Programming Language for JVM
===========================================
anubis はプロトタイプベースのオブジェクト指向言語です。  
処理系は Java で記述されていて、JVM 上で動作します。  

使い方
------

#### 対話環境(anubis.console.Main)
配布パッケージ内の `anubis.bat` を実行するか、または 

    java -cp .\lib\asm-3.1.jar;.\lib\commons-cli-1.2.jar;anubis.jar anubis.console.Main

とタイプしてください。対話環境が開始され、プロンプトが表示されます。
空行の入力で評価が開始され、実行結果が表示されます。

    anubis(1.0) on java(1.6.0_23), Windows 7
    if you want to exit, please type 'exit' or CTRL+Z.
    
    an> "Hello world".dumpString()
      > 
    --> string@1b52513a(Hello world) {
        super = [string traits];
    }

#### 対話環境(jrunscript)
jrunscript から `anubis.jar` および `asm-x.x.jar` を classpath に含めてください。

    > jrunscript -cp anubis.jar;lib\asm-3.1.jar -l anubis

対話環境が jrunscript で開始され、プロンプトが表示されます。

    anubis> "Hello world".dumpString()
    string@5a8c63a6(Hello world) {
        super = [string traits];
    }

#### JSR223
java から JSR223 を介してスクリプトエンジンを操作することができます。

    // plain eval
    ScriptEngineManager factory = new ScriptEngineManager();
    ScriptEngine engine = factory.getEngineByName("anubis");
    engine.eval(" console.puts('Hello world'); " );
    
    // invocable
    Invocable invocable = (Invocable) engine;
    engine.eval(" hello = { text -> console.puts(text) }; ");
    invocable.invokeFunction("hello", "Hello world");
    
    // compilable
    Compilable compilable = (Compilable) engine;
    CompiledScript code = compilable.compile(" console.puts('Hello Anubis!'); ");
    code.eval();

ライセンス
----------
Copyright &copy; 2011-2012 SiroKuro  
Distributed under the [MIT License][mit].  
詳細は `ANUBIS-LICENSE.TXT` を参照してください。  

[MIT]: http://www.opensource.org/licenses/mit-license.php
