package net.softsociety.prolingo.controller;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
		
		//log.debug("만들어야함");
		
		return "compilepage";
	}
	
	@PostMapping("compile")
	@ResponseBody
	public String compile(String code) {
		log.debug("compile ajax : {}", code);
		
		//현재 시간으로 파일이름 생성해서 파일 출력함
		String formatedNow = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
		String filename = "output_" + formatedNow + ".java";
		String path = "src/main/resources/static/codes/" + filename;
		//String path = filename;
		try {
			OutputStream output = new FileOutputStream(path);
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
			Runtime.getRuntime().exec("cmd /c javac /Spring/workspaceBoot/prolingo/" + path);
			//Runtime.getRuntime().exec("cmd /c javac " + path);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String result = execCmd("java testjava");
		
		log.debug(result);
		
		return result;
	}
	
	public String execCmd(String cmd) {
	    try {
	        Process process = Runtime.getRuntime().exec("cmd /c " + cmd);
	        BufferedReader reader = new BufferedReader(
	                new InputStreamReader(process.getInputStream()));
	        String line = null;
	        StringBuffer sb = new StringBuffer();
	        //sb.append(cmd);
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
