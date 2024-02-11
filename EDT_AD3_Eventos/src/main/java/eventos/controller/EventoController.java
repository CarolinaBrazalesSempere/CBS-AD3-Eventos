package eventos.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import eventos.modelo.dao.EventoDao;
import eventos.modelo.dao.ReservaDao;
import eventos.modelo.dao.TipoDao;
import eventos.modelo.dao.UsuarioDao;
import eventos.modelo.entitis.Evento;
import eventos.modelo.entitis.Reserva;
import eventos.modelo.entitis.Tipo;
import eventos.modelo.entitis.Usuario;

@Controller
@RequestMapping("/eventos")
public class EventoController {
	@Autowired
	private EventoDao edao;
	
	@Autowired
	private TipoDao tdao;
	
	@Autowired
	private ReservaDao redao;
	
	@Autowired 
	private UsuarioDao udao;
	

	@GetMapping("/activos")
	public String activos(Model model) {
		List<Evento> eventos = edao.findByActive();
		model.addAttribute("eventos", eventos);
		
		List<Tipo> tipoEvento = tdao.todos();
		model.addAttribute("tipoEvento", tipoEvento);
		
		return "eventos-activos";
	}

	@GetMapping("/destacados")
	public String destacados(Model model) {
		List<Evento> eventos = edao.findByDestacado();
		model.addAttribute("eventos", eventos);
		
		List<Tipo> tipoEvento = tdao.todos();
		model.addAttribute("tipoEvento", tipoEvento);
		
		return "eventos-destacados";
	}

	@GetMapping("/todos")
	public String allEvents(Model model) {
		List<Evento> eventos = edao.todos();
		model.addAttribute("eventos", eventos);
		
		List<Tipo> tipoEvento = tdao.todos();
		model.addAttribute("tipoEvento", tipoEvento);
		
		return "all-events";
	}

	@GetMapping("/detalle/{idEvento}")
	public String eventoDetalle(@PathVariable int idEvento, Model model, Authentication aut) {
		
		Evento eventos = edao.buscarUno(idEvento);
		model.addAttribute("eventos", eventos);
		
		List<Tipo> tipoEvento = tdao.todos();
		model.addAttribute("tipoEvento", tipoEvento);
		
		model.addAttribute("reservas", new Reserva());
		
		
		if (eventos != null) {
			//Permitir la navegación al usuario no loggeado
	        if (aut != null && aut.isAuthenticated()) {
	            // El usuario está loggeado
	            String username = aut.getName();
	            Usuario user = udao.findById(username);

	            int reservasPermitidas = 10;
	            int reservasQuedan = 0;
	            int reservasTotal = redao.eventoReservadoPorUsuario(username, idEvento);

	            reservasQuedan = reservasPermitidas - reservasTotal;

	            // Restar las reservas al aforo máximo
	            int aforoDispo = eventos.getAforoMaximo() - redao.reservasPorEvento(idEvento);
	            model.addAttribute("aforo", aforoDispo);

	            // Mostrar en el desplegable las reservas que quedan para no permitir +10
	            List<Integer> option = new ArrayList<>();
	            for (int i = 0; i <= reservasQuedan; i++) {
	                option.add(i);
	            }
	            model.addAttribute("option", option);

	            return "detalle";
	        } else {
	            
	            return "detalle";
	        }
	    }


		return "forward:/home";

	}
	
	@PostMapping("/reservar/{idEvento}")
	public String eventoReservar(@PathVariable int idEvento,@RequestParam String observaciones,  @RequestParam int cantidad, 
			 Authentication aut, Model model, RedirectAttributes ratt) {
		
		
		Evento ev = edao.buscarUno(idEvento);
		String user = aut.getName();
		Usuario usuario = udao.findById(user);
		
		int aforoDispo = ev.getAforoMaximo() - redao.reservasPorEvento(idEvento);
		if(cantidad <= aforoDispo && cantidad > 0) {
		Reserva reserva = new Reserva();
		reserva.setCantidad(cantidad);
		reserva.setEvento(ev);
		reserva.setObservaciones(observaciones);
		reserva.setPrecioVenta(ev.getPrecio());
		reserva.setUsuario(usuario);
		redao.insertOne(reserva);
		
		//La reserva se hace aquí, para cuando redirija que salga el mensaje 
		//debe estar desde donde se hace la reserva
		ratt.addFlashAttribute("mensaje","Reserva realizada con éxito");
		ratt.addFlashAttribute("reservado", true);
		model.addAttribute("reservas", reserva);
		} else {
			ratt.addFlashAttribute("errorPlazas", true);
			ratt.addFlashAttribute("errorMensaje", cantidad > 0 ? "No quedan suficientes plazas para este evento" : "No puedes reservar 0 entradas o más de 10");
			return "redirect:/eventos/detalle/" + idEvento;
		}
		
		//Redirect a reservas para que se actualicen las reservas en la app web y no solo en la BBDD
		return "redirect:/reservas";
	}

	@GetMapping("/tipo/{idTipo}")
	public String eventoTipo(@PathVariable("idTipo") int idTipo, Model model) {
		Tipo tipo = tdao.findOne(idTipo);
		
		if(tipo != null) {
			
		
		List<Tipo> tipoEvento = tdao.todos();
		model.addAttribute("tipoEvento", tipoEvento);
		
		List<Evento> evento = edao.porTipo(idTipo);
		model.addAttribute("evento", evento);
		
		return "tipo-evento";
		} else {
			return "forward:/";
		}
	}
	
	
}
