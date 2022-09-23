package net.softsociety.prolingo.controller;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("test")
public class CompilerController {

//	@Autowired
//	MemberService service;

	@GetMapping("compilepage")
	public String compilepage() {

		return "compilepage";
	}

	@PostMapping("compile")
	@ResponseBody
	public String compile(String language, String code) {
		log.debug("language : {}, code : {}", language, code);

		// 현재 시간으로 파일이름 생성해서 파일 출력함
		// 생각해보니 별로 의미 없는거같음
		// String formatedNow =
		// LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
		// String filename = "output_" + formatedNow + ".java";
		// String path = "src/main/resources/static/codes/" + filename;
		String path = "src/main/resources/static/codes/";
		String fullpath = "/Spring/workspaceBoot/prolingo/" + path;

		if (language.equals("java")) {
			String filename = "Testcode.java";
			String classname = "Testjava";

			// filewriter
			// outputstreamwriter
			try {
				// 한글이 깨짐
//				OutputStream output = new FileOutputStream(path + filename);
//				byte[] by = code.getBytes();
//				output.write(by);
//				output.close();

				// 이거로 하면 특정 단어만 됨, 먼데, 뭔데 등
//				FileWriter fw = new FileWriter(path + filename);
//				fw.write(code);
//				fw.close();

				// 위랑 동일
//				OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(path + filename));
//				osw.write(code);
//				osw.close();

				// 역시 동일
				BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(path + filename));
				byte[] by = code.getBytes();
				bos.write(by);
				bos.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			try {
				// Runtime.getRuntime().exec("cmd /c javac
				// /Spring/workspaceBoot/prolingo/src/main/resources/static/codes/test.java");
				// 한글 인코딩 문제 해결
				// https://proni.tistory.com/82
				Runtime.getRuntime().exec("cmd /c javac -encoding UTF-8 " + fullpath + filename).waitFor();

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// 한글은 안됨
			// 클래스 이름을 알아내서 넣어줘야 할 듯
			String result = execCmd("java -cp " + fullpath + " " + classname, "java");
			// String result = execCmd("javac -version");

			log.debug("실행 결과 : {}", result);

			return result;
			// return "실패!";
		} else if (language.equals("c")) {
			String filename = "testcode.c";
			String exename = "main";

//			//https://cflab2017.tistory.com/7
//			//c는 컴파일 옵션이 없고 cmd 옵션변경도 안되서 코드 자체를 수정해야 하는듯
//			//setlocale을 넣어주기 위해 main()다음 {로 잘라줌, {는 에러떠서 \\넣어줘야함
//			String[] splitcode = code.split("\\{");
//			String codec = "#include <wchar.h>\n#include <locale.h>\n";
//			
//			//헤더 넣어주고 main 다음 setlocale 다음 코드 본문의 printf를 wprintf로 대체
//			codec = codec + splitcode[0] + "{\n\tsetlocale(LC_ALL,\"\");\n" + splitcode[1].replaceAll("printf\\(", "wprintf\\(L");
//			log.debug("변경 코드 : {}", codec);
//			
//			//printf만 되는 임시 해결방안
//			//문자열은 wchar_t* str = L"가나다라"; 로 넣어야하는데다
//			//출력은 %s가 아니라 %S로 해줘야함
//			//이 외에도 바꿔야 할 게 많아서 일일히 이렇게 바꾸는건 너무 비효율적일듯

			try {
//				OutputStream output = new FileOutputStream(path + filename);
//				byte[] by = code.getBytes();
//				output.write(by);
//				output.close();

//				FileWriter fw = new FileWriter(path + filename);
//				fw.write(code);
////				fw.write(codec);
//				fw.close();

//				OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(path + filename));
//				osw.write(code);
//				osw.close();

				BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(path + filename));
				byte[] by = code.getBytes();
				bos.write(by);
				bos.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			try {
				// 내가 원하는 경로에 넣는 방법을 모르겠음, 그냥 디폴트(프로젝트 최상위) 경로로만 만들어짐
				Runtime.getRuntime().exec("cmd /c gcc -o " + exename + " " + fullpath + filename).waitFor();
			} catch (InterruptedException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// String result = execCmd("path");
			// String result = execCmd("/users/user/main");
			// String result = execCmd("/\"Program Files\"/mingw64/bin/gcc " + fullpath +
			// "main.c");
			// String result = execCmd("gcc -o gogo main.c");
			// String result = execCmd(exename);
			String result = execCmd(exename, "c");

			// String result = execCmd("/\"program Files\"/java/jdk1.8.0_321/bin/javac
			// -version");
			log.debug("실행 결과 : {}", result);

			return result;
		}

		return "false";
	}

	public String execCmd(String cmd, String language) {
		try {
			Process process = Runtime.getRuntime().exec("cmd /c " + cmd);
			// 한글 읽는거 깨지는거 해결
			// https://m.blog.naver.com/PostView.naver?isHttpsRedirect=true&blogId=slayra&logNo=221215991017
//			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
//			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream(), "MS949"));
			String charset = "";
			switch (language) {
			case "java":
				charset = "MS949";
				break;
			case "c":
				charset = "UTF-8";
				break;
			default:
				charset = "MS949";
				break;
			}
			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream(), charset));

			String line = null;
			StringBuffer sb = new StringBuffer();
			// sb.append(cmd);
			while ((line = reader.readLine()) != null) {
				sb.append(line);
				sb.append("\n");
			}
			return sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
