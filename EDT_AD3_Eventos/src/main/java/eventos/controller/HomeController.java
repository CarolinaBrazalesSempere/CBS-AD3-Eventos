package eventos.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import eventos.modelo.dao.EventoDao;
import eventos.modelo.dao.PerfilDao;
import eventos.modelo.dao.TipoDao;
import eventos.modelo.dao.UsuarioDao;
import eventos.modelo.entitis.Evento;
import eventos.modelo.entitis.Tipo;
import eventos.modelo.entitis.Usuario;

@Controller
public class HomeController {
	@Autowired
	private EventoDao edao;
	@Autowired
	private TipoDao tdao;

	@Autowired
	private UsuarioDao udao;
	
	@Autowired
	private PerfilDao perfdao;
	
	

	@GetMapping({ "", "/", "/home" })
	public String home(Model model) {


		// Mostrar en la home los eventos activos
		List<Evento> eventos = edao.findByDestAct();
		model.addAttribute("eventos", eventos);

		// Mostrar los tipos de evento para el desplegable
		List<Tipo> tipoEvento = tdao.todos();
		model.addAttribute("tipoEvento", tipoEvento);

		return "home";
	}

	@GetMapping("/login")
	public String login() {
		
		 
		return "login";
	}

	@GetMapping("/signup")
	public String signup(Model model) {

		Usuario usuario = new Usuario();
		model.addAttribute("usuario", usuario);

		return "signup";
	}

	@PostMapping("/signup")
	public String signUpProcess(Model model, Usuario usuario, RedirectAttributes ratt) {

		usuario.setEnabled(1);
		usuario.setFechaRegistro(new Date());
		usuario.addPerfil(perfdao.findById(3));
		usuario.setPassword("{noop}" + usuario.getPassword());
		if (udao.insertUser(usuario)) {
			return "redirect:/login";// le enviamos al login para que inicie sesión
		} else {
			model.addAttribute("noregistro", true);
			model.addAttribute("errorSignup", "Este usuario ya exsite");
			return "/signup";

		}
	}

}
