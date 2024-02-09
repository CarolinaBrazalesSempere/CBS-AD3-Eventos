package eventos.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import eventos.modelo.dao.ReservaDao;
import eventos.modelo.dao.TipoDao;
import eventos.modelo.dao.UsuarioDao;
import eventos.modelo.entitis.Reserva;
import eventos.modelo.entitis.Tipo;
import eventos.modelo.entitis.Usuario;

@Controller
public class ReservaController {
		
	@Autowired
	private UsuarioDao udao;
	
	@Autowired
	private TipoDao tdao;
	
	@Autowired
	private ReservaDao redao;
	
	
		@GetMapping("/reservas")
		public String misReservas(Model model, Authentication aut, RedirectAttributes ratt) {
			
			//Conseguir el nombre del usuario para obtener reservas x usuario
			String user = aut.getName();
			Usuario usuario = udao.findById(user);
			
			//Sacar las reservas por usuario
			List<Reserva> reservas = redao.reserPorUsuario(usuario);
			model.addAttribute("reservas", reservas);
			
			
			//Tipos de evento para el desplegable
			List<Tipo> tipoEvento = tdao.todos();
			model.addAttribute("tipoEvento", tipoEvento);
			
			
			return "reservas";
		}
		
	 @GetMapping("/reservas/cancelar/{idReserva}")
	 public String cancelarReserva(@PathVariable int idReserva, RedirectAttributes ratt) {
		 
		
		 if(redao.delete(idReserva) == 1) {
			 ratt.addFlashAttribute("cancelado", true);
			 ratt.addFlashAttribute("mensaje", "Reserva cancelada");
		 }
		
		 
		 return "redirect:/reservas";
	 }
		
		
}
