# BashfulJ

Annotation processor running bash commands at compile-time.
```java
@Bash("fortune | cowsay")
public class Main {
	public static void main(String[] args) {
		System.out.println(Bashing.output);
	}
}
/* Output is:
 ____________________________________
/ Don't kiss an elephant on the lips \
\ today.                             /
 ------------------------------------
        \   ^__^
         \  (oo)\_______
            (__)\       )\/\
                ||----w |
                ||     ||
*/
```
