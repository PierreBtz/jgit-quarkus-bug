# jgit-quarkus-bug

This project is a minimal example to reproduce [this issue](https://github.com/quarkiverse/quarkus-jgit/issues/200) with Quarkus and JGit.

Reproducing the issue:

* build this project in native mode (`mvn package -Pnative`)
* run the native executable (`./target/jgit-quarkus-bug-1.0.0-SNAPSHOT-runner`)

Output should look like this:

```text
java.lang.IllegalArgumentException: Enumerated values of type org.eclipse.jgit.transport.HttpConfig$HttpRedirectMode not available
	at org.eclipse.jgit.lib.Config.allValuesOf(Config.java:585)
	at org.eclipse.jgit.lib.Config.getEnum(Config.java:572)
	at org.eclipse.jgit.transport.HttpConfig.init(HttpConfig.java:305)
	at org.eclipse.jgit.transport.HttpConfig.<init>(HttpConfig.java:297)
	at org.eclipse.jgit.transport.TransportHttp.<init>(TransportHttp.java:336)
	at org.eclipse.jgit.transport.TransportHttp$1.open(TransportHttp.java:203)
	at org.eclipse.jgit.transport.Transport.open(Transport.java:558)
	at org.eclipse.jgit.api.LsRemoteCommand.execute(LsRemoteCommand.java:159)
	at org.eclipse.jgit.api.LsRemoteCommand.call(LsRemoteCommand.java:131)
	at eu.pierrebeitz.GreetingCommand.run(GreetingCommand.java:20)
	at picocli.CommandLine.executeUserObject(CommandLine.java:2045)
	at picocli.CommandLine.access$1500(CommandLine.java:148)
	at picocli.CommandLine$RunLast.executeUserObjectOfLastSubcommandWithSameParent(CommandLine.java:2469)
	at picocli.CommandLine$RunLast.handle(CommandLine.java:2461)
	at picocli.CommandLine$RunLast.handle(CommandLine.java:2423)
	at picocli.CommandLine$AbstractParseResultHandler.execute(CommandLine.java:2277)
	at picocli.CommandLine$RunLast.execute(CommandLine.java:2425)
	at io.quarkus.picocli.runtime.PicocliRunner$EventExecutionStrategy.execute(PicocliRunner.java:26)
	at picocli.CommandLine.execute(CommandLine.java:2174)
	at io.quarkus.picocli.runtime.PicocliRunner.run(PicocliRunner.java:40)
	at io.quarkus.runtime.ApplicationLifecycleManager.run(ApplicationLifecycleManager.java:141)
	at io.quarkus.runtime.Quarkus.run(Quarkus.java:80)
	at io.quarkus.runtime.Quarkus.run(Quarkus.java:51)
	at io.quarkus.runner.GeneratedMain.main(Unknown Source)
	at java.base@21.0.2/java.lang.invoke.LambdaForm$DMH/sa346b79c.invokeStaticInit(LambdaForm$DMH)
Caused by: java.lang.NoSuchMethodException: org.eclipse.jgit.transport.HttpConfig$HttpRedirectMode.values()
	at java.base@21.0.2/java.lang.Class.checkMethod(DynamicHub.java:1075)
	at java.base@21.0.2/java.lang.Class.getMethod(DynamicHub.java:1060)
	at org.eclipse.jgit.lib.Config.allValuesOf(Config.java:580)
	... 24 more
```

Modify the version to 3.3.3 and observe that the error is gone:

```text
2025-06-23 07:50:00,375 INFO  [eu.pie.GreetingCommand] (main) Got 35 refs
```

Important note: for a reason I cannot explain, on the machine building the exe the error never occurs, but copying the exe to another machine and running it there will always produce the error...
