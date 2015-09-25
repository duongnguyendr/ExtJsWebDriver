/*global Ext:false */
Ext.onReady(function () {
    Ext.create('Ext.Button', {
        text: '<code>This Should Be Disabled</code>',
        itemId: 'disabledButton',
        id: 'disabledButton',
        enableToggle: true,
        disabled: true,
        renderTo: Ext.getBody()
    });
});
