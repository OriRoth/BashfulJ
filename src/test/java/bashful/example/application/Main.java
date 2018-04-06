package bashful.example.application;

import bashful.Bashing;
import bashful.annotations.Bash;

@Bash(value = "cmd.exe /c echo \"a\"", name = "Bashing")
public class Main {
	public static void main(String[] args) {
		System.out.println(Bashing.output);
	}
}
