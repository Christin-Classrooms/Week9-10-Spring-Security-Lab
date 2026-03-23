package com.example.Thymeleaf.Demo.controllers;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.Thymeleaf.Demo.Model.Player;
import com.example.Thymeleaf.Demo.Service.PlayerService;

@Controller
public class PlayersController {

    private final PlayerService playerService;

    public PlayersController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @GetMapping("/players")
    public String getPlayers(
            @RequestParam(name = "search", required = false, defaultValue = "") String search,
            @RequestParam(name = "page", required = false, defaultValue = "0") int page,
            @RequestParam(name = "size", required = false, defaultValue = "10") int size,
            @RequestParam(name = "sort", required = false, defaultValue = "id") String sortBy,
            @RequestParam(name = "direction", required = false, defaultValue = "ASC") String direction,
            Model model){

        Sort.Direction sortedDirection = direction.equalsIgnoreCase("DESC") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Sort sort = Sort.by(sortedDirection,sortBy);

        Pageable pageable = PageRequest.of(page,size,sort);


        Page<Player> playerPage;

        if(search!=null && !search.isEmpty()){
            playerPage = playerService.findPlayerByName(search, pageable);
        }else{

            playerPage = playerService.getAllPlayersPageable(pageable);

        }

        model.addAttribute("players", playerPage.getContent());
        model.addAttribute("total", playerPage.getTotalElements());
        model.addAttribute("size", size);
        model.addAttribute("currentPage",page);
        model.addAttribute("totalPages", playerPage.getTotalPages());
        model.addAttribute("search", search);
        model.addAttribute("sort", sortBy);
        model.addAttribute("direction", direction);
        //Switching the Pagination
        model.addAttribute("hasPrevious", playerPage.hasPrevious());
        model.addAttribute("hasNext", playerPage.hasNext());
        model.addAttribute("startIndex", page * size +1);
        model.addAttribute("endIndex", Math.min((page+1)*size,(int)playerPage.getTotalElements()));

        return "Players";
    }

}
