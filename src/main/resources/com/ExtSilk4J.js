ExtSilk4J = (function(injectBlockId) {
	//if (typeof ExtSilk4J != "undefined") return ExtSilk4J;
	var injectBlockId = injectBlockId || "QA_HIDDEN";
	var silk4j_div =  document.getElementById(injectBlockId);
	
	// if silk4j_div doesn't exist, make it
	if (!silk4j_div) {
		silk4j_div = document.createElement('div');
		silk4j_div.id = injectBlockId;
		silk4j_div.setAttribute("style", "height:0px; width:0px; overflow:hidden; position:absolute; top:-999px; left:-999px;");
		document.body.appendChild(silk4j_div);
	}
	
	var htmlEscape = function(str) {
	    	return String(str)
        		.replace(/&/g, '&amp;')
        		.replace(/"/g, '&quot;')
        		.replace(/'/g, '&#39;')
        		.replace(/</g, '&lt;')
        		.replace(/>/g, '&gt;');
			}, 
		writeDataToDiv = function(data, id) {
				var dataDiv = document.getElementById(id);
				// Overwrite existing data if id exists, otherwise append to main div
				if (dataDiv) {
					dataDiv.innerHTML = htmlEscape(data);
				} else {
					silk4j_div.innerHTML += '<pre id="' + id + '">' + htmlEscape(data) + '</pre>';
				}
	};
	
	return {
		getStore: function(item) {
			Ext.getCmp(item);
			//
		},
		
		findStore: function(itemId) {
			
			
		},
		
		getSvgs : function (itemId){
			var domEl = document.getElementById(itemId);
			if (domEl) {
				return domEl.getElementsByTagName('svg') || [];
			}
			return [];
		},
		
		convertStoreToJSON : function (store) {
			if(!store){
				return "";
			}
			var dataList = Ext.Array.map(store.getRange(), function(entry, list) {
			    var data = entry.data;
			    return data;
		    });
		    
	    	//Write the first only
	    	return Ext.encode(dataList);
		},
		

    
		convertStoreToCSV : function(store) {
		    var fields = Ext.Array.map(store.model.getFields(), function(field) {
			    return field.name;
		    }), csvLines = [ fields.join(',') ];
		    Ext.each(store.getRange(), function(modelObj) {
			    var propList = [];
			    Ext.Array.forEach(fields, function(fieldName) {
				    propList.push(modelObj.data[fieldName]);
			    });
			    csvLines.push(propList.join(','));
		    });
		    return csvLines.join('\r\n');
	    },
		
		getFirstSVGStore : function (itemId){
			var store = store;
			var svgs = this.getSvgs(itemId);
			if(!svgs.length){
				return null;
			}
		    Ext.each(svgs, function(el) {
			    var container = el.parentElement,
			    	extCmp = Ext.getCmp(container.id);
			    // Component doesnt exist or doesn't have a store
			    if(!extCmp || !extCmp.getStore){
			    	return;
			    }
			    store = extCmp.getStore()
			    return true;
			});
		    
		    return store;
		},
		
		/**
		 * Returns the first store found 
		 * 
		 * @param itemId The first store found under the itemID (ex, Panel-1012, or Chart-1021) is returned
		 * @param uuid The store data is saved to this uuid for silk4j to read
		 */
		getChartAsJSON : function (itemId, uuid) {
			// find the chart & store
			var store = this.getFirstSVGStore(itemId);
			
			// convert store to string
		    var json = this.convertStoreToJSON(store);
		    
			// write string to unique id
		   writeDataToDiv(json, uuid);
		},
		
		/**
		 * Returns the first store found as a CSV object 
		 * 
		 * @param itemId The first store found under the itemID (ex, Panel-1012, or Chart-1021) is returned
		 * @param uuid The store data is saved to this uuid for silk4j to read
		 */
		getChartAsCSV : function (itemId, uuid) {
			// find the chart & store
			var store = this.getFirstSVGStore(itemId);
			
			// convert store to string
		    var csv = this.convertStoreToCSV(store);
		    
			// write string to unique id
		   writeDataToDiv(csv, uuid);
		},
		
		findVisibleComponent : function (query) {
            var queryResultArray = (window.frames[0] &&  window.frames[0].Ext) ? window.frames[0].Ext.ComponentQuery.query(query) : Ext.ComponentQuery.query(query);
            var ret = null;
            Ext.Array.every(queryResultArray, function(comp) {
            	        if (comp != null && comp.isVisible(true)){
            	            ret = comp;
            	        }
            	        return ret != null; /* return false will stop looping through array*/
            	    }
            	);
            return ret;
		},

		clickButton : function (query, uuid) {
			var comp = this.findVisibleComponent(query);
			var success = comp.btnEl.dom.click();
			writeDataToDiv(success, uuid);
		},
		
		setComboBox : function (query, item, uuid) {
			var success = false;
			var comp = this.findVisibleComponent(query);
			var store = comp.getStore();
			var index = store.find(comp.displayField, item);
			if ( index != -1 ) {
				comp.setValue(store.getAt(index));
    			success = comp.fireEvent('select', comp, [store.getAt(index)]);
			}
			
			writeDataToDiv(success, uuid);
		},
		
		setCheckbox : function (query, value, uuid) {
			var comp = this.findVisibleComponent(query);
			var success = comp.setValue(value);
			writeDataToDiv(success, uuid);
		}
	};
}("QA_HIDDEN"));