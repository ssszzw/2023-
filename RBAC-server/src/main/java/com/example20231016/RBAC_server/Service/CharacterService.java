package com.example20231016.RBAC_server.Service;

import com.example20231016.RBAC_server.Table.Character;
import com.example20231016.RBAC_server.Mapper.CharacterMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    public int deleteCharacter(Integer character_id){
        return characterMapper.delete(character_id);
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
    public List<Character> getCharactersByUserId(int user_id){
        List<Integer>characterIdList=characterMapper.getCharacterIdByUserId(user_id);
        List<Character>characterList=new ArrayList<Character>();

        for(int characterId:characterIdList){
            Character character=characterMapper.getCharacterByCharacterId(characterId);
            if (!characterList.contains(character)) {
                characterList.add(character);
            }
        }
        return characterList;
    }




}
