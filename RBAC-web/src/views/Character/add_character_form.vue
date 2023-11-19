<template>
    <el-dialog :visible.sync="dialogVisible" :title="dialogTitle">
      <el-form
        ref="formRef"
        v-loading="formLoading"
        :model="formData"
        label-width="80px" :rules="rules">
        <el-form-item label="角色名称" prop="character_name">
          <el-input v-model="formData.character_name" placeholder="请输入角色名称" />
        </el-form-item>
        <el-form-item label="角色类型" prop="character_type">
          <el-select v-model="formData.character_type" placeholder="请输入角色类型">
            <el-option label="内置" value="内置"></el-option>
            <el-option label="自定义" value="自定义"></el-option>
            <!-- <el-option v-for="type in characterTypes" :key="type" :label="type" :value="type"></el-option> -->
          </el-select>

        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-select v-model="formData.status" placeholder="选择状态">
            <el-option label="ACTIVE" value="ACTIVE"></el-option>
            <el-option label="INACTIVE" value="INACTIVE"></el-option>
          </el-select>
        </el-form-item>
        
        <!-- 删除其他不相关的表单项 -->
      </el-form>
      <template #footer>
        <el-button :disabled="formLoading" type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="closeDialog">取 消</el-button>
      </template>
    </el-dialog>
  </template>
  
  <script>
  import * as CharacterApi from '@/api/roles';
  export default {
    data() {
      return {
        dialogVisible: false,
        dialogTitle: '',
        formLoading: false,

        formData: {
          character_id: null,
          character_name: '',
          character_type: '',
          status:'',
          create_time:'',
          update_time:'',
        },
        formRef: null,
        characterTypes: [
          // { label: '类型1', value: 'type1' },
          // { label: '类型2', value: 'type2' },
          // 其他角色类型...
          '内置','自定义'
        ],
        rules: {//input的规则
          character_id: [{ required: true, message: "请输入角色id"}],
          character_name: [{ required: true, message: "请输入角色名", trigger: "blur" }],
          character_type: [{ required: true, message: "请输入类型", trigger: "blur" }],
        }
      };
    },
    methods: {
        open(type, character_id) {
            this.dialogVisible = true;

            this.dialogTitle = character_id ? '编辑角色' : '新建角色';

            // this.dialogTitle = this.$t('action.' + type);
            this.formType = type;
            console.log('this.$refs.formRef:',this.$refs.formRef);
            this.resetForm();

            // 修改时，设置数据
            if (character_id) {
                this.formLoading = true;
                try {
                // this.formData = await RoleApi.getRole(id);
                console.log("修改");
                } finally {
                this.formLoading = false;
                }
            }
        },
        resetForm() {
            this.formData = {
                character_id: null,
                character_name: '',
                character_type: '',
                status:'',
                create_time:'',
                update_time:'',
            };
            this.$nextTick(() => {
              // 确保 this.$refs.formRef 不为 null
              if (this.$refs.formRef) {
                this.$refs.formRef.resetFields();
              }
            });
        },

        closeDialog() {
            this.dialogVisible = false;
        },

        async submitForm() {
            if (!this.$refs.formRef) 
              return;
            const valid = await this.$refs.formRef.validate();
            if (!valid) 
              return;
            this.formLoading = true;

            try {

            // 调用后端保存角色的接口，传递 formData 数据
            console.log("this.formData:",this.formData);
            if (this.formType === 'create') {
              await CharacterApi.addCharacter(this.formData);
              this.$message.success('添加成功');
            } else {
              await CharacterApi.editCharacter(this.formData);
              this.$message.success('编辑成功');
            }

            this.dialogVisible = false;
            this.$emit('success');
            } catch (error) {
              console.error('添加角色失败', error);
            } finally {
              this.formLoading = false;
            }
        }
    }
  };
  </script>