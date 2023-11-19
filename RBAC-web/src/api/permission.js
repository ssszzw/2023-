import request from '@/utils/request';

// 获取所有权限
export const getAllPermissions = async () => {
  try {
    const response = await request({
      url: '/system/permission/getAllPermissions',
      method: 'get',
    });
    return response;
  } catch (error) {
    console.error('获取所有权限失败', error);
    throw error;
  }
};

// 根据角色ID获取权限ID列表
export const getPermissionIdsByCharacterId = async (character_id) => {
  try {
    const response = await request({
      url: `/system/permission/getPermissionIdsByCharacterId`,
      method: 'get',
      params: { character_id },
    });
    return response;
  } catch (error) {
    console.error('获取角色权限失败', error);
    throw error;
  }
};

// 分配权限给角色
export const assignCharacterPermissions = async (selectedPermissionIds, characterId) => {
    // console.log("selectedPermissionIds:",selectedPermissionIds,"-------characterId:",characterId);
  try {
    const response = await request({
      url: '/system/permission/assignCharacterPermissions',
      method: 'post',
      data: {
        selectedPermissionIds,
        characterId,
      },
    });
    return response;
  } catch (error) {
    console.error('分配角色权限失败', error);
    throw error;
  }
};



export const editPermission = async (formData) => {
  console.log("editPermission = async (formData):",formData);
  try {
    const response = await request({
      url: '/system/permission/editPermission',
      method: 'put',
      data: formData,
    });
    return response;
  } catch (error) {
    console.error('编辑权限失败', error);
    throw error;
  }
};

export const addPermission = async (formData) => {
  console.log("addPermission = async (formData):",formData);
  try {
    const response = await request({
      url: '/system/permission/addPermission', // 替换为实际的分配角色 API 地址
      method: 'post',
      data: formData,
    });
    return response;
  } catch (error) {
    console.error('添加权限失败', error);
    throw error;
  }
};
export const deletePermission = async (permission_id) => {
  console.log("deletePermission = async (permission_id):",permission_id);
  try {
    const response = await request({
      url: '/system/permission/deletePermission', // 替换为实际的分配角色 API 地址
      method: 'delete',
      data: { permission_id },
    });
    return response;
  } catch (error) {
    console.error('删除权限失败', error);
    throw error;
  }
};
