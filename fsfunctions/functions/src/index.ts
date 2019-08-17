import * as functions from 'firebase-functions';
import * as admin from 'firebase-admin';
admin.initializeApp(functions.config().firebase);
 // Start writing Firebase Functions
 // https://firebase.google.com/docs/functions/typescript
 
 export const helloWorld = functions.https.onRequest((request, response) => {

    let gname = request.body;

    let stime=0;
    let etime=2400;
    let venue="someplace"


    
    admin.firestore().collection('groups').doc(gname).collection("members").get().then(qs=>{
        qs.forEach(ds=>{
            venue=ds.get("venue");
            if(ds.get("stime")>stime)
            {             
                stime=ds.get("stime");
            }
            if(ds.get("etime")<etime)
            {
                etime=ds.get("etime");
            }
        })
        response.send("You should go from "+ stime+"hrs to "+etime+"hrs to "+venue+".");
     }).catch(err=>{
         response.send("failure");
        })

    });
