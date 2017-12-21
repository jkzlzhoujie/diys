package cn.temobi.complex.action.def;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.temobi.core.action.SimpleController;

import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;
import com.opensymphony.oscache.util.StringUtil;




@SuppressWarnings("serial")
@Controller  
@RequestMapping("/kaptcha/*")  
public class CaptchaController extends SimpleController {  
      
    @Autowired  
    private Producer captchaProducer = null;  
  
    @RequestMapping  
    public ModelAndView getKaptchaImage(HttpServletRequest request, HttpServletResponse response){  
        HttpSession session = request.getSession();  
        response.setDateHeader("Expires", 0);  
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");  
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");  
        response.setHeader("Pragma", "no-cache");  
        response.setContentType("image/jpeg");  
        String capText = captchaProducer.createText();  
        session.setAttribute(Constants.KAPTCHA_SESSION_KEY, capText);  
        BufferedImage bi = captchaProducer.createImage(capText);  
        ServletOutputStream out = null;
		try {
			out = response.getOutputStream();
		} catch (IOException e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}  
        try {
			ImageIO.write(bi, "jpg", out);
		} catch (IOException e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}  
        try {  
            try {
				out.flush();
			} catch (IOException e) {
				e.printStackTrace();
				log.error(e.getMessage());
			}  
        } finally {  
            try {
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
				log.error(e.getMessage());
			}  
        }  
        return null;  
    }  
    
    @RequestMapping(value="/kaptcha/checkKaptchaImage.do")
    public 
    @ResponseBody
    boolean checkKaptchaImage(HttpServletRequest request, HttpServletResponse response){  
        HttpSession session = request.getSession();  
        String code = (String)session.getAttribute(Constants.KAPTCHA_SESSION_KEY); 
        String captchaCode =request.getParameter("captcha");
        if(!StringUtil.isEmpty(captchaCode) && captchaCode.equalsIgnoreCase(code)){
        	return true;
        }
			return false;
    }
    
}  
