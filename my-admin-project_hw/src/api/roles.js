import request from '@/utils/request';

// 获取角色列表
export const getRoles = async () => {
  try {
    const response = await request({
      url: '/character/getAllCharacters',
      method: 'get',
    });
    return response;
  } catch (error) {
    console.error('获取角色列表失败', error);
    throw error;
  }
};

// 获取已分配的角色
export const getAssignedRoles = async (userId) => {
  try {
    const response = await request({
      url: `/character/getCharactersByUserId`, // 替换为实际的已分配角色 API 地址
      method: 'get',
      params:userId,
    });
    return response;
  } catch (error) {
    console.error('获取已分配角色失败', error);
    throw error;
  }
};

// 分配角色
export const assignRoles = async (selectedCharacters, userId) => {
  
  console.log("selectedCharacters:",selectedCharacters);

  try {
    const response = await request({
      url: '/character/assignCharacter', // 替换为实际的分配角色 API 地址
      method: 'post',
      data: {
        selectedCharacters,
        userId,
      },
    });
    return response;
  } catch (error) {
    console.error('分配角色失败', error);
    throw error;
  }
};

export const editCharacter = async (formData) => {
  console.log("editCharacter = async (formData):",formData);
  try {
    const response = await request({
      url: '/system/character/editCharacter', // 替换为实际的分配角色 API 地址
      method: 'put',
      data: formData,
    });
    return response;
  } catch (error) {
    console.error('编辑角色失败', error);
    throw error;
  }
};

export const addCharacter = async (formData) => {
  console.log("addCharacter = async (formData):",formData);
  try {
    const response = await request({
      url: '/system/character/addCharacter', // 替换为实际的分配角色 API 地址
      method: 'post',
      data: formData,
    });
    return response;
  } catch (error) {
    console.error('添加角色失败', error);
    throw error;
  }
};
export const deleteCharacter = async (character_id) => {
  console.log("deleteCharacter = async (character_id):",character_id);
  try {
    const response = await request({
      url: '/system/character/deleteCharacter', // 替换为实际的分配角色 API 地址
      method: 'delete',
      data: { character_id },
    });
    return response;
  } catch (error) {
    console.error('删除角色失败', error);
    throw error;
  }
};
