package com.example20231016.todo_3.Service;

import com.example20231016.todo_3.Table.Character;
import com.example20231016.todo_3.Mapper.CharacterMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CharacterService {
    @Autowired
    private CharacterMapper characterMapper;

    public Character checkCharacter(Character character){
        return characterMapper.check(character);
    }

    public int postCharacter(Character character){
        return characterMapper.post(character);
    }
    public int deleteCharacter(Character character){
        return characterMapper.delete(character);
    }
    public int putCharacter(Character character){
        return characterMapper.put(character);
    }

    public List<Character> getAllCharacters(){
        return characterMapper.getAllCharacters();
    }
    public List<Character> searchCharacters(Character character){
        return characterMapper.search(character);
    }




}
