/*global Ext:false */
Ext.onReady(function () {
  Ext.create('Ext.form.Checkbox', {
                boxLabel: 'MyCheckbox',
                name: 'myCheckbox',
                inputValue: '1',
        		renderTo: Ext.getBody()
    });
});
