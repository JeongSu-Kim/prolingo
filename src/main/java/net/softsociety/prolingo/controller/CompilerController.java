package net.softsociety.prolingo.controller;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
			
			try {
				OutputStream output = new FileOutputStream(path + filename);
				byte[] by = code.getBytes();
				output.write(by);
				output.close();
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
				Runtime.getRuntime().exec("cmd /c javac " + fullpath + filename).waitFor();

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// 한글은 안됨
			// 클래스 이름을 알아내서 넣어줘야 할 듯
			String result = execCmd("java -cp " + fullpath + " " + classname);
			// String result = execCmd("javac -version");

			log.debug("실행 결과 : {}", result);

			return result;
			// return "실패!";
		}
		else if (language.equals("c")) {
			String filename = "testcode.c";
			String exename = "main";
			
			try {
				OutputStream output = new FileOutputStream(path + filename);
				byte[] by = code.getBytes();
				output.write(by);
				output.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			try {
				//내가 원하는 경로에 넣는 방법을 모르겠음, 그냥 디폴트(프로젝트 최상위) 경로로만 만들어짐 
				Runtime.getRuntime().exec("cmd /c gcc -o " + exename + " " + fullpath + filename).waitFor();
			} catch (InterruptedException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			//String result = execCmd("path");
			//String result = execCmd("/users/user/main");
			//String result = execCmd("/\"Program Files\"/mingw64/bin/gcc " + fullpath + "main.c");
			//String result = execCmd("gcc -o gogo main.c");
			String result = execCmd(exename);

			//String result = execCmd("/\"program Files\"/java/jdk1.8.0_321/bin/javac -version");
			log.debug("실행 결과 : {}", result);

			return result;
		}
		
		return "false";
	}

	public String execCmd(String cmd) {
		try {
			Process process = Runtime.getRuntime().exec("cmd /c " + cmd);
			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
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
