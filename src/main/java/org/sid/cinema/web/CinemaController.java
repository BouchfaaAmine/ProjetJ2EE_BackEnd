package org.sid.cinema.web;

import java.util.List;

import javax.validation.Valid;

import org.sid.cinema.dao.CinemaRepository;
import org.sid.cinema.entities.Cinema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CinemaController {
	@Autowired
	private CinemaRepository cinemaRepository;
	
	@GetMapping(path="/cinema")
	public String list(Model model,
			@RequestParam(name="page", defaultValue = "0")int p,
			@RequestParam(name="size", defaultValue = "5")int s,
			@RequestParam(name="keyword", defaultValue = "")String kw) {
		Page<Cinema> pageCinemas=cinemaRepository.findByNameContains(kw, PageRequest.of(p, s));
		model.addAttribute("listCinemas", pageCinemas.getContent());
		model.addAttribute("pages", new int[pageCinemas.getTotalPages()]);
		model.addAttribute("currentPage", p);
		model.addAttribute("keyword", kw);
		return "cinema";
	}
	
	@GetMapping(path="/deleteCinema")
	public String delete(Long id) {
		cinemaRepository.deleteById(id);
		return "redirect:/cinema";
	}
	
	@GetMapping(path="/formCinema")
	public String formCinema(Model model) {
		model.addAttribute("cinema", new Cinema());
		model.addAttribute("mode", "new");
		return "formCinema";
	}
	
	@PostMapping(path="/saveCinema")
	public String saveCinema(Model model,@Valid Cinema cinema, BindingResult bindingResult) {
		if(bindingResult.hasErrors()) return "formCinema";
		cinemaRepository.save(cinema);
		model.addAttribute("cinema", cinema);
		//return "redirect:/cinema";
		return "verification";
	}
	
	@GetMapping(path="/editCinema")
	public String editCinema(Model model, Long id) {
		Cinema c=cinemaRepository.findById(id).get();
		model.addAttribute("cinema", c);
		model.addAttribute("mode", "edit");
		return "formCinema";
	}
	
}
