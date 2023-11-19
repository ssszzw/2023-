package com.example20231016.RBAC_server.Mapper;

import com.example20231016.RBAC_server.Table.Character;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface CharacterMapper {

    Character check(Character character);

    int post(Character character);
    int delete(Integer character_id);
    int put(Character character);
    List<Character> getAllCharacters();
    List<Character> search(Character character);
    List<Integer> getCharacterIdByUserId(int user_id);
//    List<Character> getCharacterByCharacterId(int character_id);
    List<Character> getCharactersByUserId(int user_id);
    Character getCharacterByCharacterId(int character_id);

    int saveCharacterPermission(Integer character_id,Integer permission_id);
    int deleteCharacterPermission(Integer character_id,Integer permission_id);
}
