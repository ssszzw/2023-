<template>
    <el-dialog :visible.sync="dialogVisible" title="菜单权限" @close="closeDialog">
      <el-form ref="formRef" v-loading="formLoading" :model="formData" label-width="80px">
        <el-form-item label="角色id">
          <el-tag>{{ formData.character_id }}</el-tag>
        </el-form-item>
        <el-form-item label="角色名称">
          <el-tag>{{ formData.character_name }}</el-tag>
        </el-form-item>
        <!-- <el-form-item label="角色标识">
          <el-tag>{{ formData.code }}</el-tag>
        </el-form-item> -->
        <el-form-item label="权限tree">
          <el-card class="cardHeight">
            <template slot="header">
              全选/全不选:
              <el-switch v-model="treeNodeAll" active-text="是" inactive-text="否" inline-prompt @change="handleCheckedTreeNodeAll" />
              全部展开/折叠:
              <el-switch v-model="menuTreeExpand" active-text="展开" inactive-text="折叠" inline-prompt @change="handleCheckedTreeExpand" />
            </template>
            <el-tree ref="treeRef" :data="menuTreeOptions" :props="defaultProps" empty-text="加载中，请稍候" node-key="id" show-checkbox />
          </el-card>
        </el-form-item>
      </el-form>
      <template slot="footer">
        <el-button :disabled="formLoading" type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="dialogVisible = false">取 消</el-button>
      </template>
    </el-dialog>
  </template>
  
  <script>

  import * as PermissionApi from '@/api/permission';
  
  export default {
    data() {
      return {
        dialogVisible: false,
        formLoading: false,
        formData: {
          id: 0,
          name: '',
          // code: '',
          permissionIds: [],
        },
        menuTreeOptions: [],
        menuTreeExpand: false,
        treeNodeAll: false,
        // treeRef: null,
        defaultProps: {//树的默认属性
          children: 'children',
          label: 'label',
        },
      }
    },
    methods: {
      async convertPermissionToTreeNode(permissions, parentId = 0) {
        const treeNodes = [];

        for (const permission of permissions) {
          if (permission.parent_id === parentId) {
            const treeNode = {
              id: permission.permission_id,
              label: permission.permission_name,
              children: await this.convertPermissionToTreeNode(permissions, permission.permission_id),
            };
            treeNodes.push(treeNode);
          }
        }

        return treeNodes;
      },
      async open(row) {
        this.dialogVisible = true;
        this.resetForm();
        // this.menuTreeOptions = handleTree(await MenuApi.getSimpleMenusList());
        // this.menuTreeOptions = await PermissionApi.getAllPermissions();
        const allPermissions=await PermissionApi.getAllPermissions();
        this.menuTreeOptions = await this.convertPermissionToTreeNode(allPermissions.data);
        this.formData = {

          character_id: row.character_id,
          character_name: row.character_name,
          // code: row.code,
          permissionIds: (await PermissionApi.getPermissionIdsByCharacterId(row.character_id)).data,
        };
        this.formLoading = false;
        this.$nextTick(() => {
          this.formData.permissionIds.forEach((menuId) => {
            this.$refs.treeRef.setChecked(menuId, true, false);
          });
        });
      },
      async submitForm() {
        if (!this.$refs.formRef) return;
        const valid = await this.$refs.formRef.validate();
        if (!valid) return;
        this.formLoading = true;
        try {
          const data = {
            character_id: this.formData.character_id,
            permissionIds: [
              ...Array.from(this.$refs.treeRef.getCheckedKeys(false)),
              ...Array.from(this.$refs.treeRef.getHalfCheckedKeys()),
            ],
          };
          // console.log("data.character_id:",data.character_id,"-------data:",data);
          await PermissionApi.assignCharacterPermissions(data.permissionIds,data.character_id);
          this.$message.success('操作成功');
          this.dialogVisible = false;
          this.$emit('success');
        } finally {
          this.formLoading = false;
        }
      },
      resetForm() {
        this.treeNodeAll = false;
        this.menuTreeExpand = false;
        this.formData = {
          character_id: 0,
          character_name: '',
          // code: '',
          permissionIds: [],
        };
        // this.$nextTick(() => {
        //   this.$refs.treeRef.setCheckedNodes([]);
        //   this.$refs.formRef.resetFields();
        // });
        this.$nextTick(() => {
          // 确保 this.$refs.formRef 不为 null
          if (this.$refs.formRef) {
            this.$refs.treeRef.setCheckedNodes([]);
            this.$refs.formRef.resetFields();
          }
        });
      },
      handleCheckedTreeNodeAll() {
        this.$refs.treeRef.setCheckedNodes(this.treeNodeAll ? this.menuTreeOptions : []);
      },
      handleCheckedTreeExpand() {
        const nodes = this.$refs.treeRef.store.nodesMap;
        for (const node in nodes) {
          if (nodes[node].expanded === this.menuTreeExpand) continue;
          nodes[node].expanded = this.menuTreeExpand;
        }
      },
      closeDialog() {
        this.dialogVisible = false;
      },
    },
  };
  </script>
  
  <style lang="scss" scoped>
  .cardHeight {
    width: 100%;
    max-height: 400px;
    overflow-y: scroll;
  }
  </style>
  