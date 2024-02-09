package eventos.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import eventos.modelo.dao.EventoDao;
import eventos.modelo.entitis.Evento;

/*test para las peticiones*/
@RestController
public class EventosRestController {
	
	

		@Autowired
		private EventoDao edao;
		
		@GetMapping("/all")
		public List<Evento> todos(){
			return edao.todos();
			
		}
		
		
		@GetMapping("/tip/{idTipo}")
		public List<Evento> eventoTipo(@PathVariable int idTipo, Model model) {

			List<Evento> ev = edao.porTipo(idTipo);
			
			return ev;
		}
		

	
}
